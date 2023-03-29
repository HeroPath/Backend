package com.gianca1994.heropathbackend.resources.market.utilities;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.utils.Const;

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
        if (!exists) throw new BadReq(Const.MARKET.SELLER_NOT_FOUND.getMsg());
    }

    public void checkInventoryFull(int inventorySize) {
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new BadReq(Const.MARKET.INVENTORY_FULL.getMsg());
    }

    public void checkMaxItemsPublished(int itemsPublished) {
        if (itemsPublished >= SvConfig.MAX_ITEM_PUBLISHED) throw new BadReq(Const.MARKET.MAX_ITEMS_PUBLISHED.getMsg());
    }

    public void checkMaxGoldPrice(long goldPrice) {
        if (goldPrice >= SvConfig.MAX_GOLD_PRICE) throw new BadReq(Const.MARKET.MAX_GOLD_PRICE.getMsg());
    }

    public void checkMaxDiamondPrice(int diamondPrice) {
        if (diamondPrice >= SvConfig.MAX_DIAMOND_PRICE) throw new BadReq(Const.MARKET.MAX_DIAMOND_PRICE.getMsg());
    }

    public void checkItemAlreadyInMarket(boolean inMarket) {
        if (inMarket) throw new BadReq(Const.MARKET.ITEM_ALREADY_IN_MARKET.getMsg());
    }

    public void checkItemOwned(long itemUserId, long userId) {
        if (itemUserId != userId) throw new BadReq(Const.MARKET.ITEM_NOT_OWNED.getMsg());
    }

    public void checkEnoughGold(long userGold, long goldPrice) {
        if (userGold < goldPrice) throw new BadReq(Const.MARKET.NOT_ENOUGH_GOLD.getMsg());
    }

    public void checkEnoughDiamond(long userDiamond, int diamondPrice) {
        if (userDiamond < diamondPrice) throw new BadReq(Const.MARKET.NOT_ENOUGH_DIAMOND.getMsg());
    }

}
