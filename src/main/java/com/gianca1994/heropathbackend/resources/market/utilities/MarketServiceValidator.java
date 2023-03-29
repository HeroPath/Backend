package com.gianca1994.heropathbackend.resources.market.utilities;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to validate the data sent by the client.
 */

public class MarketServiceValidator {

    public void checkItemExists(boolean exists) {
        if (!exists) throw new BadReq(MarketConst.ITEM_NOT_FOUND);
    }

    public void checkUserExists(boolean exists) {
        if (!exists) throw new BadReq(MarketConst.USER_NOT_FOUND);
    }

    public void checkSellerExists(boolean exists) {
        if (!exists) throw new BadReq(MarketConst.SELLER_NOT_FOUND);
    }

    public void checkInventoryFull(int inventorySize) {
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new BadReq(MarketConst.INVENTORY_FULL);
    }

    public void checkMaxItemsPublished(int itemsPublished) {
        if (itemsPublished >= SvConfig.MAXIMUM_ITEMS_PUBLISHED) throw new BadReq(MarketConst.MAX_ITEMS_PUBLISHED);
    }

    public void checkMaxGoldPrice(long goldPrice) {
        if (goldPrice >= SvConfig.MAXIMUM_GOLD_PRICE) throw new BadReq(MarketConst.MAX_GOLD_PRICE);
    }

    public void checkMaxDiamondPrice(int diamondPrice) {
        if (diamondPrice >= SvConfig.MAXIMUM_DIAMOND_PRICE) throw new BadReq(MarketConst.MAX_DIAMOND_PRICE);
    }

    public void checkItemAlreadyInMarket(boolean inMarket) {
        if (inMarket) throw new BadReq(MarketConst.ITEM_ALREADY_IN_MARKET);
    }

    public void checkItemOwned(long itemUserId, long userId) {
        if (itemUserId != userId) throw new BadReq(MarketConst.ITEM_NOT_OWNED);
    }

    public void checkEnoughGold(long userGold, long goldPrice) {
        if (userGold < goldPrice) throw new BadReq(MarketConst.NOT_ENOUGH_GOLD);
    }

    public void checkEnoughDiamond(long userDiamond, int diamondPrice) {
        if (userDiamond < diamondPrice) throw new BadReq(MarketConst.NOT_ENOUGH_DIAMOND);
    }

}
