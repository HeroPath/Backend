package com.gianca1994.aowebbackend.config;

public class ModifConfig {
    /**
     * @Author: Gianca1994
     * Explanation: This class is used to modify the configuration of the application.
     */

    /////////////////////////// USER ///////////////////////////
    public static final short START_LVL = 1;
    public static final long START_EXP = 0;
    public static final long START_GOLD = 0;
    public static final int START_DIAMOND = 1000;
    public static final int START_FREE_SKILL_POINTS = 5;
    public static final int FREE_SKILL_POINTS_PER_LEVEL = 2;
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

    /////////////////////////// INITIAL CLASS ///////////////////////////
    // MAGE
    public static final int MAGE_START_STR = 1;
    public static final int MAGE_START_DEX = 1;
    public static final int MAGE_START_INT = 3;
    public static final int MAGE_START_VIT = 2;
    public static final int MAGE_START_LUK = 2;
    // WARRIOR
    public static final int WARRIOR_START_STR = 3;
    public static final int WARRIOR_START_DEX = 1;
    public static final int WARRIOR_START_INT = 1;
    public static final int WARRIOR_START_VIT = 3;
    public static final int WARRIOR_START_LUK = 1;
    // ARCHER
    public static final int ARCHER_START_STR = 1;
    public static final int ARCHER_START_DEX = 3;
    public static final int ARCHER_START_INT = 1;
    public static final int ARCHER_START_VIT = 2;
    public static final int ARCHER_START_LUK = 2;
    /////////////////////////// INITIAL CLASS ///////////////////////////

    /////////////////////////// CLASSES MULTIPLIER ///////////////////////////
    // MAGE
    public static final String MAGE_NAME = "mage";
    public static final int MIN_DMG_MAGE = 5;
    public static final int MAX_DMG_MAGE = 7;
    public static final int MAX_HP_MAGE = 10;
    public static final int DEFENSE_MAGE = 2;
    public static final int EVASION_MAGE = 2;
    public static final float CRITICAL_MAGE = 0.15f;
    // WARRIOR
    public static final String WARRIOR_NAME = "warrior";
    public static final int MIN_DMG_WARRIOR = 3;
    public static final int MAX_DMG_WARRIOR = 5;
    public static final int MAX_HP_WARRIOR = 20;
    public static final int DEFENSE_WARRIOR = 5;
    public static final int EVASION_WARRIOR = 2;
    public static final float CRITICAL_WARRIOR = 0.1f;
    // ARCHER
    public static final String ARCHER_NAME = "archer";
    public static final int MIN_DMG_ARCHER = 4;
    public static final int MAX_DMG_ARCHER = 6;
    public static final int MAX_HP_ARCHER = 15;
    public static final int DEFENSE_ARCHER = 3;
    public static final int EVASION_ARCHER = 4;
    public static final float CRITICAL_ARCHER = 0.125f;
    /////////////////////////// CLASSES MULTIPLIER ///////////////////////////
}
