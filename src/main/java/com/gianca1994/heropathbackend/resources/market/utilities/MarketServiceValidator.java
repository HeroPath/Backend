package com.gianca1994.heropathbackend.resources.market.utilities;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadRequest;

public class MarketServiceValidator {

    public void checkItemExists(boolean exists) {
        if (!exists) throw new BadRequest(MarketConst.ITEM_NOT_FOUND);
    }

    public void checkUserExists(boolean exists) {
        if (!exists) throw new BadRequest(MarketConst.USER_NOT_FOUND);
    }

    public void checkSellerExists(boolean exists) {
        if (!exists) throw new BadRequest(MarketConst.SELLER_NOT_FOUND);
    }

    public void checkInventoryFull(int inventorySize) {
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new BadRequest(MarketConst.INVENTORY_FULL);
    }

    public void checkMaxItemsPublished(int itemsPublished) {
        if (itemsPublished >= SvConfig.MAXIMUM_ITEMS_PUBLISHED) throw new BadRequest(MarketConst.MAX_ITEMS_PUBLISHED);
    }

    public void checkMaxGoldPrice(long goldPrice) {
        if (goldPrice >= SvConfig.MAXIMUM_GOLD_PRICE) throw new BadRequest(MarketConst.MAX_GOLD_PRICE);
    }

    public void checkMaxDiamondPrice(int diamondPrice) {
        if (diamondPrice >= SvConfig.MAXIMUM_DIAMOND_PRICE) throw new BadRequest(MarketConst.MAX_DIAMOND_PRICE);
    }

    public void checkItemAlreadyInMarket(boolean inMarket) {
        if (inMarket) throw new BadRequest(MarketConst.ITEM_ALREADY_IN_MARKET);
    }

    public void checkItemOwned(long itemUserId, long userId) {
        if (itemUserId != userId) throw new BadRequest(MarketConst.ITEM_NOT_OWNED);
    }

    public void checkEnoughGold(long userGold, long goldPrice) {
        if (userGold < goldPrice) throw new BadRequest(MarketConst.NOT_ENOUGH_GOLD);
    }

    public void checkEnoughDiamond(long userDiamond, int diamondPrice) {
        if (userDiamond < diamondPrice) throw new BadRequest(MarketConst.NOT_ENOUGH_DIAMOND);
    }

}
