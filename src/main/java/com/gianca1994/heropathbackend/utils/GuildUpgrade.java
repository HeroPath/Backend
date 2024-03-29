package com.gianca1994.heropathbackend.utils;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains the cost of upgrading a guild
 */

public class GuildUpgrade {
    public static final int[] DIAMONDS = new int[]{
            2000, 3000, 4500, 6750, 10125, 15188, 22781, 34172, 51258, 76887
    };

    public static int getDiamondCost(int level) {
        return DIAMONDS[level - 1];
    }
}



