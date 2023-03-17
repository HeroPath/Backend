package com.gianca1994.heropathbackend.config;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the statics of the server.
 */

public class SvConfig {

    /////////////////////////// BASIC STATICS ///////////////////////////
    public static int EXPERIENCE_MULTIPLIER = 100000;
    public static int GOLD_MULTIPLIER = 1000;
    public static final short LEVEL_MAX = 300;
    public static final int PVE_PTS_MAX = 2000;
    public static final int PVP_PTS_MAX = 1000;
    /////////////////////////// BASIC STATICS ///////////////////////////

    /////////////////////////// DIAMONDS ///////////////////////////
    public static final int DIAMOND_DROP_CHANCE_PERCENTAGE = 50;
    public static final int MAXIMUM_AMOUNT_DIAMONDS_DROP = 100;
    /////////////////////////// DIAMONDS ///////////////////////////

    /////////////////////////// PVP WIN OR LOSS RATE ///////////////////////////
    public static final float PVP_GOLD_WIN_RATE = 0.25f;
    public static final float PVP_GOLD_LOSS_RATE = 0.1f;
    /////////////////////////// PVP WIN OR LOSS RATE ///////////////////////////

    /////////////////////////// TITLE POINTS ///////////////////////////
    public static final int PVP_MIN_RATE_POINT_TITLE = 50;
    public static final int PVP_MAX_RATE_POINT_TITLE = 100;
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

    /////////////////////////// QUEST ///////////////////////////
    public static final int MAX_ACTIVE_QUESTS = 3;
    public static final int QUEST_PER_PAGE = 5;
    /////////////////////////// QUEST ///////////////////////////

    /////////////////////////// GUILD ///////////////////////////
    public static final int MAX_MEMBERS_IN_GUILD = 5;
    public static final int LEVEL_TO_CREATE_GUILD = 100;
    public static final int GOLD_TO_CREATE_GUILD = 5000000;
    public static final int DIAMOND_TO_CREATE_GUILD = 100;
    public static final int LEVEL_TO_JOIN_GUILD = 10;
    public static final int GUILD_LVL_MAX = 10;
    public static final int USER_PER_PAGE = 5;
    /////////////////////////// GUILD ///////////////////////////

    ////////////////////////// ROLE //////////////////////////
    public static final String STANDARD_ROLE = "STANDARD";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final List<String> ADMIN_LIST = Arrays.asList(
            "admin", "gianca", "lucho", "renzo"
    );
    ////////////////////////// ROLE //////////////////////////

    /////////////////////////// EVENT ///////////////////////////
    public static String EVENT_ACTIVE = "NONE";
    /////////////////////////// EVENT ///////////////////////////

}
