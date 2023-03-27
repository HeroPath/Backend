package com.gianca1994.heropathbackend.config;

public enum ItemDropConfig {
    POTION(0.5);

    private final double dropPercentage = 0.1;
    private final int maxDrop = 2;


    ItemDropConfig(double v) {
    }
}

