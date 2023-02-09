package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.config.GuildUpgradeConfig;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.item.ItemConst;
import com.gianca1994.aowebbackend.resources.user.User;

import java.util.Objects;

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
        if (guild == null) throw new NotFound("Guild not found");
    }

    public void checkUserInGuild(String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user is in a guild
         * @param String guildName
         * @return void
         */
        if (!Objects.equals(guildName, "")) throw new Conflict("You are already in a guild");
    }

    public void checkUserNotInGuild(String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user is not in a guild
         * @param String guildName
         * @return void
         */
        if (Objects.equals(guildName, "")) throw new Conflict("You are not in a guild");
    }

    public void guildNameExist(boolean guildExist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a guild exists
         * @param boolean guildExist
         * @return void
         */
        if (guildExist) throw new Conflict("Guild already exists");
    }

    public void guildTagExist(boolean tagExist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a guild tag exists
         * @param boolean tagExist
         * @return void
         */
        if (tagExist) throw new Conflict("Tag already exists");
    }

    public void guildDtoReqToSaveGuild(GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the guildDTO has the required fields to save a guild
         * @param GuildDTO guildDTO
         * @return void
         */
        if (guildDTO.getName() == null) throw new Conflict("Name is required");
        if (guildDTO.getDescription() == null) throw new Conflict("Description is required");
        if (guildDTO.getTag() == null) throw new Conflict("Tag is required");
    }

    public void guildReqToCreate(int level, long gold, int diamond) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user has the required fields to create a guild
         * @param int level
         * @param long gold
         * @param int diamond
         * @return void
         */
        int lvlReq = SvConfig.LEVEL_TO_CREATE_GUILD;
        if (level < lvlReq) throw new Conflict("You need to be level " + lvlReq + " to create a guild");

        long goldReq = SvConfig.GOLD_TO_CREATE_GUILD;
        if (gold < goldReq) throw new Conflict("You need " + goldReq + " gold to create a guild");

        int diamondReq = SvConfig.DIAMOND_TO_CREATE_GUILD;
        if (diamond < diamondReq) throw new Conflict("You need " + diamondReq + " diamonds to create a guild");
    }

    public void reqLvlToReqGuild(int level) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user has the required level to request a guild
         * @param int level
         * @return void
         */
        int lvlReq = SvConfig.LEVEL_TO_JOIN_GUILD;
        if (level < lvlReq) throw new Conflict("You need to be level " + lvlReq + " to join a guild");
    }

    public void checkGuildIsFull(int membersSize) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the guild is full
         * @param int membersSize
         * @return void
         */
        if (membersSize >= SvConfig.MAX_MEMBERS_IN_GUILD) throw new Conflict("Guild is full");
    }

    public void checkGuildLeaderOrSubLeader(boolean isLeaderOrSubLeader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader or subleader of the guild
         * @param boolean isLeaderOrSubLeader
         * @return void
         */
        if (!isLeaderOrSubLeader) throw new Conflict("You are not the leader or sub leader of the guild");
    }

    public void checkOtherUserInGuild(String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is in a guild
         * @param String guildName
         * @return void
         */
        if (!Objects.equals(guildName, "")) throw new Conflict("User is already in a guild");
    }

    public void checkUserInReqGuild(boolean userInRequest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is in the guild requests
         * @param boolean userInRequest
         * @return void
         */
        if (!userInRequest) throw new Conflict("User is not in the guild requests");
    }

    public void checkUserIsLeader(String username, String leader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader of the guild
         * @param String username
         * @param String leader
         * @return void
         */
        if (Objects.equals(username, leader)) throw new Conflict("User is already leader");
    }

    public void checkUserRemoveLeader(String username, String nameRemove, String leader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user is the leader of the guild
         * @param String username
         * @param String nameRemove
         * @param String leader
         * @return void
         */
        if (!Objects.equals(nameRemove, username))
            if (Objects.equals(username, leader)) throw new Conflict("You cannot remove the guild leader");
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
            throw new Conflict("You cannot remove the leader because there is no sub leader to take command");
    }

    ////////////////////////////////////////////////////////////////////////

    public void donateDiamonds(String guildName, int diamonds, int userDiamonds) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method donates diamonds to a guild
         * @param String guildName
         * @param int diamonds
         * @param int userDiamonds
         * @return void
         */
        if (Objects.equals(guildName, "")) throw new Conflict("You are not in a guild");
        if (userDiamonds < diamonds) throw new Conflict("You don't have enough diamonds");
    }

    public void upgradeLevel(String guildName, boolean isLeaderOrSubLeader,
                             int guildLevel, int guildDiamonds) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method upgrades the level of a guild
         * @param String guildName
         * @param boolean isLeaderOrSubLeader
         * @param int guildLevel
         * @param int userDiamonds
         * @return void
         */
        if (Objects.equals(guildName, "")) throw new Conflict("You are not in a guild");
        if (!isLeaderOrSubLeader)
            throw new Conflict("You are not the leader or subleader of your guild");
        if (guildLevel >= SvConfig.GUILD_LVL_MAX) throw new Conflict("Your guild is already at the maximum level");
        if (guildDiamonds < GuildUpgradeConfig.getDiamondCost(guildLevel))
            throw new Conflict("Your guild doesn't have enough diamonds");
    }

}
