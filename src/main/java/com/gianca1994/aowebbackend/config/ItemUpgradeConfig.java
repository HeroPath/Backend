package com.gianca1994.aowebbackend.config;

/**
 * @Author: Gianca1994
 * Explanation: Class used to add the quality to the item.
 */

public class ItemUpgradeConfig {

    public static final String[] QUALITY_NAME = new String[]{
            "white", "green", "blue", "purple", "orange", "red"
    };

    public static final int[] ITEM_UPGRADE_DIAMOND_COST = new int[]{
            100, 200, 300, 400
    };

    public static String getName(int quality) {
        return QUALITY_NAME[quality - 1];
    }

    public static int getCost(int itemLevel) {
        return ITEM_UPGRADE_DIAMOND_COST[itemLevel - 1];
    }
}
