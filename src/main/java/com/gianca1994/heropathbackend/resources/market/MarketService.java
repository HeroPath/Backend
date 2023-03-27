package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadRequest;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import com.gianca1994.heropathbackend.resources.market.utilities.MarketConst;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class MarketService {

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
        if (!itemR.existsById(marketRegisterDTO.getItemId())) throw new BadRequest(MarketConst.ITEM_NOT_FOUND);
        if (marketR.countItemsPublishedByUserId(userId) >= SvConfig.MAXIMUM_ITEMS_PUBLISHED)
            throw new BadRequest(MarketConst.MAX_ITEMS_PUBLISHED);
        if (marketRegisterDTO.getGoldPrice() >= SvConfig.MAXIMUM_GOLD_PRICE)
            throw new BadRequest(MarketConst.MAX_GOLD_PRICE);
        if (marketRegisterDTO.getDiamondPrice() >= SvConfig.MAXIMUM_DIAMOND_PRICE)
            throw new BadRequest(MarketConst.MAX_DIAMOND_PRICE);

        Item item = itemR.findById(marketRegisterDTO.getItemId()).get();
        if (item.isInMarket()) throw new BadRequest(MarketConst.ITEM_ALREADY_IN_MARKET);
        if (!Objects.equals(item.getUser().getId(), userId)) throw new BadRequest(MarketConst.ITEM_NOT_OWNED);
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
        validateMarketAndUsersExist(userId, marketId);
        Market market = marketR.findById(marketId).get();
        Inventory userInventory = userR.findInventoryById(userId);

        if (userInventory.getItems().size() >= SvConfig.MAX_ITEMS_INVENTORY)
            throw new BadRequest(MarketConst.INVENTORY_FULL);
        userInventory.getItems().add(market.getItem());
        marketR.delete(market);
    }

    @Transactional
    public void buyItem(Long userId, Long marketId) throws Conflict {
        validateMarketAndUsersExist(userId, marketId);

        Market market = marketR.findById(marketId).get();
        if (!userR.existsById(market.getUserId())) throw new Conflict(MarketConst.SELLER_NOT_FOUND);

        buyItemWithGold(userId, market);
        buyItemWithDiamond(userId, market);
        addGoldAndDiamondToSeller(market);
        saveItemAndInventory(userId, market);
        marketR.delete(market);
    }

    /////////////////////////// PRIVATE METHODS ///////////////////////////
    private void validateMarketAndUsersExist(Long userId, Long marketId) {
        if (!userR.existsById(userId)) throw new BadRequest(MarketConst.USER_NOT_FOUND);
        if (!marketR.existsById(marketId)) throw new BadRequest(MarketConst.ITEM_NOT_FOUND);
    }

    private void buyItemWithGold(Long userId, Market market) {
        Long itemGoldPrice = market.getGoldPrice();
        Long userBuyerGold = userR.findGoldByUserId(userId);

        if (userBuyerGold < itemGoldPrice) throw new BadRequest(MarketConst.NOT_ENOUGH_GOLD);

        userR.updateGoldByUserId(userId, userBuyerGold - itemGoldPrice);
    }

    private void buyItemWithDiamond(Long userId, Market market) {
        int itemDiamondPrice = market.getDiamondPrice();
        int userBuyerDiamond = userR.findDiamondByUserId(userId);

        if (userBuyerDiamond < itemDiamondPrice) throw new BadRequest(MarketConst.NOT_ENOUGH_DIAMOND);

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
        Item item = market.getItem();
        item.setUser(userR.findById(userId).get());
        item.setInMarket(false);
        itemR.save(item);

        Inventory userBuyerInventory = userR.findInventoryById(userId);
        userBuyerInventory.getItems().add(market.getItem());
        userR.updateInventoryById(userId, userBuyerInventory);
    }
}
