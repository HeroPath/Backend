package com.gianca1994.heropathbackend.utils;

import com.gianca1994.heropathbackend.config.SvConfig;

public class Constants {
    public enum NPC {
        NOT_FOUND("Npc not found"),
        NOT_IN_ZONE("No npcs found in that area"),
        NAME_EMPTY("Name cannot be empty"),
        LVL_LT_1("Level cannot be less than 1"),
        GIVE_MIN_EXP_LT_0("GiveMinExp cannot be less than 0"),
        GIVE_MAX_EXP_LT_0("GiveMaxExp cannot be less than 0"),
        GIVE_MIN_GOLD_LT_0("GiveMinGold cannot be less than 0"),
        GIVE_MAX_GOLD_LT_0("GiveMaxGold cannot be less than 0"),
        HP_LT_0("Hp cannot be less than 0"),
        MAX_HP_LT_0("MaxHp cannot be less than 0"),
        MIN_DMG_LT_0("MinDmg cannot be less than 0"),
        MAX_DMG_LT_0("MaxDmg cannot be less than 0"),
        MIN_DEF_LT_0("MinDefense cannot be less than 0"),
        ZONE_EMPTY("Zone cannot be empty");

        private final String message;

        NPC(String message) {
            this.message = message;
        }

        public String msg() {
            return message;
        }
    }

    public enum GUILD {
        NOT_FOUND("Guild not found"),
        NAME_ALREADY_EXIST("Guild name already exist"),
        TAG_ALREADY_EXIST("Guild tag already exist"),
        ALREADY_IN_GUILD("You are already in a guild"),
        NOT_INSIDE("You are not in a guild"),
        NAME_REQUIRED("Name is required"),
        DESCRIPTION_REQUIRED("Description is required"),
        TAG_REQUIRED("Tag is required"),
        FULL("Guild is full"),
        NOT_LEADER_OR_SUBLEADER("You are not the leader or sub leader of the guild"),
        USER_IN_GUILD("User is already in a guild"),
        USER_NOT_REQUEST("User is not in the guild requests"),
        USER_ALREADY_LEADER("User is already leader"),
        CANNOT_REMOVE_LEADER("You cannot remove the guild leader"),
        NO_SUBLEADER("You cannot remove the leader because there is no sub leader to take command"),
        YOU_NOT_ENOUGH_DIAMONDS("You don't have enough diamonds"),
        LVL_MAX("Your guild is already at the maximum level"),
        NOT_ENOUGH_DIAMONDS("Your guild doesn't have enough diamonds"),
        LVL_REQUEST("You need to be level " + SvConfig.LEVEL_TO_JOIN_GUILD + " to join a guild"),
        LVL_REQ("You need to be level " + SvConfig.LEVEL_TO_CREATE_GUILD + " to create a guild"),
        GOLD_REQ("You need " + SvConfig.GOLD_TO_CREATE_GUILD + " gold to create a guild"),
        DIAMOND_REQ("You need " + SvConfig.DIAMOND_TO_CREATE_GUILD + " diamonds to create a guild");

        private final String message;

        GUILD(String message) {
            this.message = message;
        }

        public String msg() {
            return message;
        }
    }
}
