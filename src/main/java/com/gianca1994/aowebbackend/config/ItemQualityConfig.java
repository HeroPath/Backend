package com.gianca1994.aowebbackend.config;

/**
 * @Author: Gianca1994
 * Explanation:
 */

public class ItemQualityConfig {

    public static final String[] QUALITY_NAME = new String[]{
            "white", "green", "blue", "purple", "orange", "red"
    };

    public static String getName(int quality) {
        return QUALITY_NAME[quality - 1];
    }
}
