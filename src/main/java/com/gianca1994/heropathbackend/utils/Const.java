package com.gianca1994.heropathbackend.utils;

import com.gianca1994.heropathbackend.config.SvConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class Const {

    @Getter
    public enum USER {
        NOT_FOUND("User not found"),
        DONT_HAVE_SKILLPTS("You don't have any free skill points"),
        SKILLS_ENABLED(Arrays.asList("strength", "dexterity", "intelligence", "vitality", "luck")),
        ALLOWED_SKILLPTS("Skill point name must be one of the following: "),
        IMPOSSIBLE_ATTACK_LESS_HP("Impossible to attack with less than 15% of life"),
        CANT_ATTACK_LVL_LOWER_5("You can't attack with a level lower than 5"),
        CANT_ATTACK_YOURSELF("You can't fight yourself"),
        CANT_ATTACK_ADMIN("You can't attack an admin"),
        CANT_ATTACK_NPC_LVL_HIGHER_5("You can't attack an npc with level higher than 5 levels higher than you"),
        CANT_ATTACK_NPC_SEA("You can't attack an npc in the sea without a ship"),
        CANT_ATTACK_NPC_HELL("You can't attack an npc in hell without wings"),
        DONT_HAVE_PVE_PTS("You don't have any free pve points"),
        DONT_HAVE_PVP_PTS("You don't have any free pvp points"),
        CANT_ATTACK_GUILD_MEMBER("You can't attack a guild member");

        private final String msg;
        private final List<String> skills;

        USER(String message) {
            this.msg = message;
            skills = null;
        }

        USER(List<String> skills) {
            this.msg = "";
            this.skills = skills;
        }
    }

    @Getter
    @AllArgsConstructor
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

        private final String msg;
    }

    @Getter
    @AllArgsConstructor
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

        private final String msg;
    }

    @Getter
    @AllArgsConstructor
    public enum JWT {
        PASS_INCORRECT("Password incorrect"),
        TOKEN_PREFIX("Bearer "),
        HEADER_STRING("Authorization"),
        EMAIL_PATTERN("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(.[A-Za-z0-9]+)(.[A-Za-z]{2,})$"),
        USER_DISABLED("USER_DISABLED"),
        UNAUTHORIZED("Unauthorized"),
        UNABLE_GET_TOKEN("Unable to get JWT Token"),
        TOKEN_EXPIRED("JWT Token has expired"),
        TOKEN_ADULTERATED("JWT adulterated"),
        EMAIL_NOT_VALID("Invalid email address"),
        USER_NOT_VALID("Username must be alphanumeric"),
        USER_PATTERN("^[a-zA-Z0-9]*$"),
        USER_EXISTS("Username already exists"),
        EMAIL_EXISTS("Email already exists"),
        USER_LENGTH("Username must be between 3 and 20 characters"),
        PASS_LENGTH("Password must be between 3 and 20 characters"),
        CLASS_NOT_FOUND("Class not found");

        private final String msg;
    }
}
