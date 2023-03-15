package com.gianca1994.heropathbackend.resources.stats;

public class StatsConfig {
    public static StatsModel statsModel = new StatsModel(0, 0, 0, 0);

    public static void addUserStat() {
        statsModel.addUserStat();
    }
}
