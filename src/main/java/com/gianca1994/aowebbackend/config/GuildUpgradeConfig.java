package com.gianca1994.aowebbackend.config;

public class GuildUpgradeConfig {
    public static final int[] DIAMONDS = new int[]{
            100, 200, 400, 800, 1600, 3200, 6400, 12800, 25600
    };

    public static int getDiamondCost(int level) {
        return DIAMONDS[level - 1];
    }
}
