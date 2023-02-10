package com.gianca1994.aowebbackend.resources.guild.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the constants used in the guild package
 */

public class GuildConst {
    public static final String GUILD_NOT_FOUND = "Guild not found";
    public static final String GUILD_NAME_EXIST = "Guild name already exist";
    public static final String GUILD_TAG_EXIST = "Guild tag already exist";
    public static final String ALREADY_IN_GUILD = "You are already in a guild";
    public static final String NOT_IN_GUILD = "You are not in a guild";
    public static final String NAME_REQUIRED = "Name is required";
    public static final String DESCRIPTION_REQUIRED = "Description is required";
    public static final String TAG_REQUIRED = "Tag is required";
    public static final String GUILD_FULL = "Guild is full";
    public static final String NOT_LEADER_OR_SUB_LEADER = "You are not the leader or sub leader of the guild";
    public static final String USER_IN_GUILD = "User is already in a guild";
    public static final String USER_NOT_IN_REQUEST = "User is not in the guild requests";
    public static final String USER_ALREADY_LEADER = "User is already leader";
    public static final String CANNOT_REMOVE_LEADER = "You cannot remove the guild leader";
    public static final String NO_SUB_LEADER = "You cannot remove the leader because there is no sub leader to take command";
    public static final String NOT_ENOUGH_DIAMONDS = "You don't have enough diamonds";
    public static final String GUILD_LVL_MAX = "Your guild is already at the maximum level";
    public static final String GUILD_NOT_ENOUGH_DIAMONDS = "Your guild doesn't have enough diamonds";
    public static final String GUILD_LVL_REQ = "You need to be level " + SvConfig.LEVEL_TO_CREATE_GUILD + " to create a guild";
    public static final String GUILD_GOLD_REQ = "You need " + SvConfig.GOLD_TO_CREATE_GUILD + " gold to create a guild";
    public static final String GUILD_DIAMOND_REQ = "You need " + SvConfig.DIAMOND_TO_CREATE_GUILD + " diamonds to create a guild";
}
