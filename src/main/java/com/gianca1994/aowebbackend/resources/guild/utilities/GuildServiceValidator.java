package com.gianca1994.aowebbackend.resources.guild.utilities;

import com.gianca1994.aowebbackend.config.GuildUpgradeConfig;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.Guild;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemConst;
import com.gianca1994.aowebbackend.resources.user.User;

import java.util.Objects;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the methods to validate the guild service
 */

public class GuildServiceValidator {

    public void userFound(boolean userExist) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user exists
         * @param boolean userExist
         * @return void
         */
        if (!userExist) throw new NotFound(ItemConst.USER_NOT_FOUND);
    }

    public void userFoundByObject(User user) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user exists
         * @param User user
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
    }

    public void guildFound(Guild guild) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a guild exists
         * @param Guild guild
         * @return void
         */
        if (guild == null) throw new NotFound(GuildConst.GUILD_NOT_FOUND);
    }

    public void guildFoundByName(boolean guildExist) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a guild exists
         * @param boolean guildExist
         * @return void
         */
        if (!guildExist) throw new NotFound(GuildConst.GUILD_NOT_FOUND);
    }

    public void checkUserInGuild(String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user is in a guild
         * @param String guildName
         * @return void
         */
        if (!Objects.equals(guildName, "")) throw new Conflict(GuildConst.ALREADY_IN_GUILD);
    }

    public void checkUserNotInGuild(String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user is not in a guild
         * @param String guildName
         * @return void
         */
        if (Objects.equals(guildName, "")) throw new Conflict(GuildConst.NOT_IN_GUILD);
    }

    public void guildNameExist(boolean guildExist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a guild exists
         * @param boolean guildExist
         * @return void
         */
        if (guildExist) throw new Conflict(GuildConst.GUILD_NAME_EXIST);
    }

    public void guildTagExist(boolean tagExist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a guild tag exists
         * @param boolean tagExist
         * @return void
         */
        if (tagExist) throw new Conflict(GuildConst.GUILD_TAG_EXIST);
    }

    public void guildDtoReqToSaveGuild(GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the guildDTO has the required fields to save a guild
         * @param GuildDTO guildDTO
         * @return void
         */
        if (guildDTO.getName() == null) throw new Conflict(GuildConst.NAME_REQUIRED);
        if (guildDTO.getDescription() == null) throw new Conflict(GuildConst.DESCRIPTION_REQUIRED);
        if (guildDTO.getTag() == null) throw new Conflict(GuildConst.TAG_REQUIRED);
    }

    public void guildReqToCreate(int level, long gold, int diamond) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user has the required level, gold and diamond to create a guild
         * @param int level
         * @param long gold
         * @param int diamond
         * @return void
         */
        if (level < SvConfig.LEVEL_TO_CREATE_GUILD) throw new Conflict(GuildConst.GUILD_LVL_REQ);
        if (gold < SvConfig.GOLD_TO_CREATE_GUILD) throw new Conflict(GuildConst.GUILD_GOLD_REQ);
        if (diamond < SvConfig.DIAMOND_TO_CREATE_GUILD) throw new Conflict(GuildConst.GUILD_DIAMOND_REQ);
    }

    public void reqLvlToReqGuild(int level) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user has the required level to request a guild
         * @param int level
         * @return void
         */
        if (level < SvConfig.LEVEL_TO_JOIN_GUILD) throw new Conflict(GuildConst.GUILD_LVL_REQUEST);
    }

    public void checkGuildIsFull(int membersSize) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the guild is full
         * @param int membersSize
         * @return void
         */
        if (membersSize >= SvConfig.MAX_MEMBERS_IN_GUILD) throw new Conflict(GuildConst.GUILD_FULL);
    }

    public void checkGuildLeaderOrSubLeader(boolean isLeaderOrSubLeader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader or sub leader of the guild
         * @param boolean isLeaderOrSubLeader
         * @return void
         */
        if (!isLeaderOrSubLeader) throw new Conflict(GuildConst.NOT_LEADER_OR_SUB_LEADER);
    }

    public void checkOtherUserInGuild(String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is in a guild
         * @param String guildName
         * @return void
         */
        if (!Objects.equals(guildName, "")) throw new Conflict(GuildConst.USER_IN_GUILD);
    }

    public void checkUserInReqGuild(boolean userInRequest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is in the guild requests
         * @param boolean userInRequest
         * @return void
         */
        if (!userInRequest) throw new Conflict(GuildConst.USER_NOT_IN_REQUEST);
    }

    public void checkUserIsLeader(String username, String leader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader of the guild
         * @param String username
         * @param String leader
         * @return void
         */
        if (Objects.equals(username, leader)) throw new Conflict(GuildConst.USER_ALREADY_LEADER);
    }

    public void checkUserRemoveLeader(String nameRemove, String leader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader of the guild
         * @param String username
         * @param String nameRemove
         * @param String leader
         * @return void
         */
        if (Objects.equals(nameRemove, leader)) throw new Conflict(GuildConst.CANNOT_REMOVE_LEADER);
    }

    public void checkRemoveLeaderNotSubLeader(String nameRemove, String leader, String subLeader,
                                              int memberSize) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader of the guild
         * @param String nameRemove
         * @param String leader
         * @param String subLeader
         * @param int memberSize
         * @return void
         */
        if (nameRemove.equals(leader) && subLeader.equals("") && memberSize > 1)
            throw new Conflict(GuildConst.NO_SUB_LEADER);
    }

    public void checkUserDiamondsForDonate(int userDiamonds, int diamonds) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user has enough diamonds to donate
         * @param int userDiamonds
         * @param int diamonds
         * @return void
         */
        if (userDiamonds < diamonds) throw new Conflict(GuildConst.NOT_ENOUGH_DIAMONDS);
    }

    public void checkGuildLvlMax(int guildLevel) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the guild is at the maximum level
         * @param int guildLevel
         * @return void
         */
        if (guildLevel >= SvConfig.GUILD_LVL_MAX) throw new Conflict(GuildConst.GUILD_LVL_MAX);
    }

    public void checkGuildDiamondsForUpgrade(int guildDiamonds, int guildLevel) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the guild has enough diamonds to upgrade
         * @param int guildDiamonds
         * @param int guildLevel
         * @return void
         */
        if (guildDiamonds < GuildUpgradeConfig.getDiamondCost(guildLevel))
            throw new Conflict(GuildConst.GUILD_NOT_ENOUGH_DIAMONDS);
    }
}
