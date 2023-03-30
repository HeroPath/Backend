package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import com.gianca1994.heropathbackend.resources.market.dto.response.MarketAllDTO;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class is the Market Service
 */

@Service
public class MarketService {

    Validator validate = new Validator();

    @Autowired
    private MarketRepository marketR;

    @Autowired
    private ItemRepository itemR;

    @Autowired
    private UserRepository userR;

    public MarketAllDTO getAllMarkets(Long userId) {
        return new MarketAllDTO(
                userR.findGoldByUserId(userId),
                userR.findDiamondByUserId(userId),
                marketR.findAllExceptUserId(userId)
        );
    }

    public List<Market> getMyMarkets(Long userId) {
        return marketR.findAllByUserId(userId);
    }

    public void registerItem(Long userId, String usernameSeller, MarketRegisterDTO marketRegisterDTO) {
        validate.itemExist(itemR.existsById(marketRegisterDTO.getItemId()));
        validate.maxItemsPublished(marketR.countItemsPublishedByUserId(userId));
        validate.maxGoldPrice(marketRegisterDTO.getGoldPrice());
        validate.maxDiamondPrice(marketRegisterDTO.getDiamondPrice());

        Item item = itemR.findById(marketRegisterDTO.getItemId()).get();
        validate.itemAlreadyMarket(item.isInMarket());
        validate.itemOwned(item.getUser().getId(), userId);
        item.setInMarket(true);
        item.setUser(null);

        Inventory userInventory = userR.findInventoryById(userId);
        userInventory.getItems().remove(item);

        userR.updateInventoryById(userId, userInventory);
        itemR.save(item);

        marketR.save(new Market(
                userId,
                item,
                usernameSeller,
                marketRegisterDTO.getGoldPrice(),
                marketRegisterDTO.getDiamondPrice()
        ));
    }

    public void removeItemMarket(Long userId, Long marketId) throws Conflict {
        checkUserAndItemExists(userId, marketId);
        Market market = marketR.findById(marketId).get();
        saveItemAndInventory(userId, market);
        marketR.delete(market);
    }

    @Transactional
    public void buyItem(Long userId, Long marketId) throws Conflict {
        checkUserAndItemExists(userId, marketId);
        Market market = marketR.findById(marketId).get();
        validate.sellerExist(userR.existsById(market.getUserId()));

        buyItemWithGold(userId, market);
        buyItemWithDiamond(userId, market);
        addGoldAndDiamondToSeller(market);
        saveItemAndInventory(userId, market);
        marketR.delete(market);
    }

    /////////////////////////// PRIVATE METHODS ///////////////////////////
    private void checkUserAndItemExists(Long userId, Long marketId) throws Conflict {
        validate.userExist(userR.existsById(userId));
        validate.itemExist(marketR.existsById(marketId));
    }

    private void buyItemWithGold(Long userId, Market market) {
        Long userBuyerGold = userR.findGoldByUserId(userId);
        Long itemGoldPrice = market.getGoldPrice();
        validate.enoughGold(userBuyerGold, itemGoldPrice);
        userR.updateGoldByUserId(userId, userBuyerGold - itemGoldPrice);
    }

    private void buyItemWithDiamond(Long userId, Market market) {
        int userBuyerDiamond = userR.findDiamondByUserId(userId);
        int itemDiamondPrice = market.getDiamondPrice();
        validate.enoughDiamond(userBuyerDiamond, itemDiamondPrice);
        userR.updateUserDiamond(userId, userBuyerDiamond - itemDiamondPrice);
    }

    private void addGoldAndDiamondToSeller(Market market) {
        Long userSellerId = market.getUserId();

        Long itemGoldPrice = market.getGoldPrice();
        Long userSellerGold = (long) (userR.findGoldByUserId(userSellerId) + (itemGoldPrice * (1 - SvConfig.GOLD_FEES_PERCENTAGE)));
        userR.updateGoldByUserId(userSellerId, userSellerGold);

        int itemDiamondPrice = market.getDiamondPrice();
        int userSellerDiamond = (int) (userR.findDiamondByUserId(userSellerId) + (itemDiamondPrice * (1 - SvConfig.DIAMOND_FEES_PERCENTAGE)));
        userR.updateUserDiamond(userSellerId, userSellerDiamond);
    }

    private void saveItemAndInventory(Long userId, Market market) throws Conflict {
        Inventory userInventory = userR.findInventoryById(userId);
        validate.inventoryFull(userInventory.getItems().size());

        Item item = market.getItem();
        item.setUser(userR.findById(userId).get());
        item.setInMarket(false);
        itemR.save(item);

        userInventory.getItems().add(market.getItem());
        userR.updateInventoryById(userId, userInventory);
    }
}
