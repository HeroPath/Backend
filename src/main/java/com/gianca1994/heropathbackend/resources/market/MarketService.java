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

    Validator validator = new Validator();

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
        validator.itemExist(itemR.existsById(marketRegisterDTO.getItemId()));
        validator.maxItemsPublished(marketR.countItemsPublishedByUserId(userId));
        validator.maxGoldPrice(marketRegisterDTO.getGoldPrice());
        validator.maxDiamondPrice(marketRegisterDTO.getDiamondPrice());

        Item item = itemR.findById(marketRegisterDTO.getItemId()).get();
        validator.itemAlreadyMarket(item.isInMarket());
        validator.itemOwned(item.getUser().getId(), userId);
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
        validator.sellerExist(userR.existsById(market.getUserId()));

        buyItemWithGold(userId, market);
        buyItemWithDiamond(userId, market);
        addGoldAndDiamondToSeller(market);
        saveItemAndInventory(userId, market);
        marketR.delete(market);
    }

    /////////////////////////// PRIVATE METHODS ///////////////////////////
    private void checkUserAndItemExists(Long userId, Long marketId) throws Conflict {
        validator.userExist(userR.existsById(userId));
        validator.itemExist(marketR.existsById(marketId));
    }

    private void buyItemWithGold(Long userId, Market market) {
        Long userBuyerGold = userR.findGoldByUserId(userId);
        Long itemGoldPrice = market.getGoldPrice();
        validator.enoughGold(userBuyerGold, itemGoldPrice);
        userR.updateGoldByUserId(userId, userBuyerGold - itemGoldPrice);
    }

    private void buyItemWithDiamond(Long userId, Market market) {
        int userBuyerDiamond = userR.findDiamondByUserId(userId);
        int itemDiamondPrice = market.getDiamondPrice();
        validator.enoughDiamond(userBuyerDiamond, itemDiamondPrice);
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
        validator.inventoryFull(userInventory.getItems().size());

        Item item = market.getItem();
        item.setUser(userR.findById(userId).get());
        item.setInMarket(false);
        itemR.save(item);

        userInventory.getItems().add(market.getItem());
        userR.updateInventoryById(userId, userInventory);
    }
}
