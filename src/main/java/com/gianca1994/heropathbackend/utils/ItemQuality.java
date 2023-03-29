package com.gianca1994.heropathbackend.utils;

/**
 * @Author: Gianca1994
 * @Explanation: Class used to add the quality to the item.
 */

public class ItemQuality {

    public static final String[] QUALITY_NAME = new String[]{
            "white", "green", "blue", "purple", "orange", "red"
    };

    public static String getQuality(int quality) {
        return QUALITY_NAME[quality - 1];
    }
}
