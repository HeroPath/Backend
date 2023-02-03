package com.gianca1994.aowebbackend.config;

import com.gianca1994.aowebbackend.resources.classes.Class;

import java.util.Arrays;
import java.util.List;

public class ModifConfig {
    /**
     * @Author: Gianca1994
     * Explanation: This class is used to modify the configuration of the application.
     */

    /////////////////////////// USER ///////////////////////////
    public static final short START_LVL = 1;
    public static final long START_EXP = 0;
    public static final long START_GOLD = 1000;
    public static final int START_DIAMOND = 100;
    public static final int START_FREE_SKILL_POINTS = 3;
    public static final int FREE_SKILL_POINTS_PER_LEVEL = 1;
    public static final int MAX_CRITICAL_PERCENTAGE = 50;
    /////////////////////////// USER ///////////////////////////

    /////////////////////////// TITLE ///////////////////////////
    // TITLE 1
    public static final String TITLE1_NAME = "iron";
    public static final int TITLE1_MIN_LVL = 1;
    public static final int TITLE1_MIN_PTS = 0;
    public static final int TITLE1_STR = 0;
    public static final int TITLE1_DEX = 0;
    public static final int TITLE1_INT = 0;
    public static final int TITLE1_VIT = 0;
    public static final int TITLE1_LUK = 0;

    // TITLE 2
    public static final String TITLE2_NAME = "bronze";
    public static final int TITLE2_MIN_LVL = 10;
    public static final int TITLE2_MIN_PTS = 50;
    public static final int TITLE2_STR = 5;
    public static final int TITLE2_DEX = 5;
    public static final int TITLE2_INT = 5;
    public static final int TITLE2_VIT = 5;
    public static final int TITLE2_LUK = 5;

    // TITLE 3
    public static final String TITLE3_NAME = "silver";
    public static final int TITLE3_MIN_LVL = 20;
    public static final int TITLE3_MIN_PTS = 100;
    public static final int TITLE3_STR = 10;
    public static final int TITLE3_DEX = 10;
    public static final int TITLE3_INT = 10;
    public static final int TITLE3_VIT = 10;
    public static final int TITLE3_LUK = 10;

    // TITLE 4
    public static final String TITLE4_NAME = "gold";
    public static final int TITLE4_MIN_LVL = 40;
    public static final int TITLE4_MIN_PTS = 200;
    public static final int TITLE4_STR = 20;
    public static final int TITLE4_DEX = 20;
    public static final int TITLE4_INT = 20;
    public static final int TITLE4_VIT = 20;
    public static final int TITLE4_LUK = 20;

    // TITLE 5
    public static final String TITLE5_NAME = "platinum";
    public static final int TITLE5_MIN_LVL = 80;
    public static final int TITLE5_MIN_PTS = 400;
    public static final int TITLE5_STR = 40;
    public static final int TITLE5_DEX = 40;
    public static final int TITLE5_INT = 40;
    public static final int TITLE5_VIT = 40;
    public static final int TITLE5_LUK = 40;

    // TITLE 6
    public static final String TITLE6_NAME = "diamond";
    public static final int TITLE6_MIN_LVL = 150;
    public static final int TITLE6_MIN_PTS = 1000;
    public static final int TITLE6_STR = 100;
    public static final int TITLE6_DEX = 100;
    public static final int TITLE6_INT = 100;
    public static final int TITLE6_VIT = 100;
    public static final int TITLE6_LUK = 100;

    // TITLE 7
    public static final String TITLE7_NAME = "challenger";
    public static final int TITLE7_MIN_LVL = 200;
    public static final int TITLE7_MIN_PTS = 2500;
    public static final int TITLE7_STR = 200;
    public static final int TITLE7_DEX = 200;
    public static final int TITLE7_INT = 200;
    public static final int TITLE7_VIT = 200;
    public static final int TITLE7_LUK = 200;
    /////////////////////////// TITLE ///////////////////////////

    /////////////////////////// CLASS ////////////////////////////
    public static final List<Class> CLASSES = Arrays.asList(
            new Class("mage", 1, 1, 3, 2, 2, 5, 7, 10, 2, 2, 0.15f),
            new Class("warrior", 3, 1, 1, 3, 1, 3, 5, 20, 5, 2, 0.1f),
            new Class("archer", 1, 3, 1, 2, 2, 4, 6, 15, 3, 4, 0.125f)
    );
    /////////////////////////// CLASS ////////////////////////////

}
