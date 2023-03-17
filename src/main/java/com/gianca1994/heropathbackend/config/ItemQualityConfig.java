package com.gianca1994.heropathbackend.config;

/**
 * @Author: Gianca1994
 * @Explanation: Class used to add the quality to the item.
 */

public class ItemQualityConfig {

    public static final String[] QUALITY_NAME = new String[]{
            "white", "green", "blue", "purple", "orange", "red"
    };

    public static String getName(int quality) {
        return QUALITY_NAME[quality - 1];
    }
}
