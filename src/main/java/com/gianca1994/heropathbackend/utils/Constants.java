package com.gianca1994.heropathbackend.utils;

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
}
