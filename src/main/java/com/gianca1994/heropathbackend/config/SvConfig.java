package com.gianca1994.heropathbackend.config;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the statics of the server.
 */

public class SvConfig {

    /////////////////////////// BASIC STATICS ///////////////////////////
    public static int EXPERIENCE_MULTIPLIER = 1000;
    public static int GOLD_MULTIPLIER = 100;
    public static final short LEVEL_MAX = 350;
    public static final int PVE_PTS_MAX = 5000;
    public static final int PVP_PTS_MAX = 5000;
    /////////////////////////// BASIC STATICS ///////////////////////////

    /////////////////////////// DIAMONDS ///////////////////////////
    public static final int DIAMOND_DROP_CHANCE_PERCENTAGE = 5;
    public static final int MAXIMUM_AMOUNT_DIAMONDS_DROP = 10;
    /////////////////////////// DIAMONDS ///////////////////////////

    /////////////////////////// PVP WIN OR LOSS RATE ///////////////////////////
    public static final float PVP_GOLD_WIN_RATE = 0.25f;
    public static final float PVP_GOLD_LOSS_RATE = 0.1f;
    /////////////////////////// PVP WIN OR LOSS RATE ///////////////////////////

    /////////////////////////// TITLE POINTS ///////////////////////////
    public static final int PVP_MIN_RATE_POINT_TITLE = 20;
    public static final int PVP_MAX_RATE_POINT_TITLE = 40;
    /////////////////////////// TITLE POINTS ///////////////////////////

    /////////////////////////// PVP ///////////////////////////
    public static final float MIN_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED = 0.15f;
    public static final int CRITICAL_DAMAGE_MULTIPLIER = 2;
    public static final int MAX_LEVEL_DIFFERENCE = 5;
    /////////////////////////// PVP ///////////////////////////

    /////////////////////////// ITEM ///////////////////////////
    public static final int MAX_ITEMS_INVENTORY = 24;
    public static final int MAX_ITEM_LEVEL = 5;
    /////////////////////////// ITEM ///////////////////////////

    /////////////////////////// RANKING ///////////////////////////
    public static final int USER_PER_PAGE = 10;
    /////////////////////////// RANKING ///////////////////////////

    /////////////////////////// QUEST ///////////////////////////
    public static final int MAX_ACTIVE_QUESTS = 5;
    /////////////////////////// QUEST ///////////////////////////

    /////////////////////////// GUILD ///////////////////////////
    public static final int NUMBER_INITIAL_MEMBERS_GUILD = 5;
    public static final int LEVEL_TO_CREATE_GUILD = 100;
    public static final int GOLD_TO_CREATE_GUILD = 5000000;
    public static final int DIAMOND_TO_CREATE_GUILD = 100;
    public static final int LEVEL_TO_JOIN_GUILD = 10;
    public static final int GUILD_LVL_MAX = 10;
    /////////////////////////// GUILD ///////////////////////////

    ////////////////////////// ROLE //////////////////////////
    public static final String STANDARD_ROLE = "STANDARD";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final List<String> ADMIN_LIST = Arrays.asList(
            "admin", "gianca", "lucho", "renzo"
    );
    ////////////////////////// ROLE //////////////////////////

    /////////////////////////// EVENT ///////////////////////////
    public static String EVENT_ACTIVE_MSG = "NONE";
    public static final String EVENT_NONE = "NONE";

    public static final String EVENT_EXP_X2 = "EXP X2";
    public static final boolean EVENT_EXP_X2_ACTIVE = true;
    public static final String EVENT_EXP_X2_DATE_START = "0 0 0 ? * SAT"; // SATURDAY
    public static final String EVENT_EXP_X2_DATE_END = "0 0 0 ? * SUN"; // SUNDAY

    public static final String EVENT_GOLD_X2 = "GOLD X2";
    public static final boolean EVENT_GOLD_X2_ACTIVE = false;
    public static final String EVENT_GOLD_X2_DATE_START = "0 0 0 ? * SUN"; // SUNDAY
    public static final String EVENT_GOLD_X2_DATE_END = "0 0 0 ? * MON"; // MONDAY
    /////////////////////////// EVENT ///////////////////////////

    /////////////////////////// MARKET ///////////////////////////
    public static final int MAXIMUM_ITEMS_PUBLISHED = 5;
    public static final float GOLD_FEES_PERCENTAGE = 0.1F;
    public static final float DIAMOND_FEES_PERCENTAGE = 0.1F;
    public static final int MAXIMUM_GOLD_PRICE = 1000000000;
    public static final int MAXIMUM_DIAMOND_PRICE = 100000000;
    /////////////////////////// MARKET ///////////////////////////

}
