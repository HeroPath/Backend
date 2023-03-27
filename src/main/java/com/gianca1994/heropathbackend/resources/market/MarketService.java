package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import com.gianca1994.heropathbackend.resources.market.utilities.MarketServiceValidator;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
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

    MarketServiceValidator validator = new MarketServiceValidator();

    @Autowired
    private MarketRepository marketR;

    @Autowired
    private ItemRepository itemR;

    @Autowired
    private UserRepository userR;

    public List<Market> getAllMarkets(Long userId) {
        return marketR.findAllExceptUserId(userId);
    }

    public List<Market> getMyMarkets(Long userId) {
        return marketR.findAllByUserId(userId);
    }

    public void registerItem(Long userId, String usernameSeller, MarketRegisterDTO marketRegisterDTO) {
        validator.checkItemExists(itemR.existsById(marketRegisterDTO.getItemId()));
        validator.checkMaxItemsPublished(marketR.countItemsPublishedByUserId(userId));
        validator.checkMaxGoldPrice(marketRegisterDTO.getGoldPrice());
        validator.checkMaxDiamondPrice(marketRegisterDTO.getDiamondPrice());

        Item item = itemR.findById(marketRegisterDTO.getItemId()).get();
        validator.checkItemAlreadyInMarket(item.isInMarket());
        validator.checkItemOwned(item.getUser().getId(), userId);
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

    public void removeItemMarket(Long userId, Long marketId) {
        validator.checkUserExists(userR.existsById(userId));
        validator.checkItemExists(marketR.existsById(marketId));
        Market market = marketR.findById(marketId).get();

        saveItemAndInventory(userId, market);
        marketR.delete(market);
    }

    @Transactional
    public void buyItem(Long userId, Long marketId) {
        validator.checkUserExists(userR.existsById(userId));
        validator.checkItemExists(marketR.existsById(marketId));

        Market market = marketR.findById(marketId).get();
        validator.checkSellerExists(userR.existsById(market.getUserId()));

        buyItemWithGold(userId, market);
        buyItemWithDiamond(userId, market);
        addGoldAndDiamondToSeller(market);
        saveItemAndInventory(userId, market);
        marketR.delete(market);
    }

    /////////////////////////// PRIVATE METHODS ///////////////////////////
    private void buyItemWithGold(Long userId, Market market) {
        Long userBuyerGold = userR.findGoldByUserId(userId);
        Long itemGoldPrice = market.getGoldPrice();
        validator.checkEnoughGold(userBuyerGold, itemGoldPrice);
        userR.updateGoldByUserId(userId, userBuyerGold - itemGoldPrice);
    }

    private void buyItemWithDiamond(Long userId, Market market) {
        int userBuyerDiamond = userR.findDiamondByUserId(userId);
        int itemDiamondPrice = market.getDiamondPrice();
        validator.checkEnoughDiamond(userBuyerDiamond, itemDiamondPrice);
        userR.updateUserDiamond(userId, userBuyerDiamond - itemDiamondPrice);
    }

    private void addGoldAndDiamondToSeller(Market market) {
        Long userSellerId = market.getUserId();

        Long itemGoldPrice = market.getGoldPrice();
        Long userSellerGold = (long) (userR.findGoldByUserId(userSellerId) + (itemGoldPrice * SvConfig.GOLD_FEES_PERCENTAGE));
        userR.updateGoldByUserId(userSellerId, userSellerGold);

        int itemDiamondPrice = market.getDiamondPrice();
        int userSellerDiamond = (int) (userR.findDiamondByUserId(userSellerId) + (itemDiamondPrice * SvConfig.DIAMOND_FEES_PERCENTAGE));
        userR.updateUserDiamond(userSellerId, userSellerDiamond);
    }

    private void saveItemAndInventory(Long userId, Market market) {
        Inventory userInventory = userR.findInventoryById(userId);
        validator.checkInventoryFull(userInventory.getItems().size());

        Item item = market.getItem();
        item.setUser(userR.findById(userId).get());
        item.setInMarket(false);
        itemR.save(item);

        userInventory.getItems().add(market.getItem());
        userR.updateInventoryById(userId, userInventory);
    }
}
