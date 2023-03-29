package com.gianca1994.heropathbackend.resources.guild.utilities;

import com.gianca1994.heropathbackend.utils.Const;
import com.gianca1994.heropathbackend.utils.GuildUpgrade;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.guild.Guild;
import com.gianca1994.heropathbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.heropathbackend.resources.user.User;

import java.util.Objects;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the methods to validate the guild service
 */

public class GuildServiceValidator {

    public void userExist(boolean exist) throws NotFound {
        if (!exist) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void userExistByObject(User user) throws NotFound {
        if (user == null) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void guildExist(boolean exist) throws NotFound {
        if (!exist) throw new NotFound(Const.GUILD.NOT_FOUND.getMsg());
    }

    public void guildExistByObject(Guild guild) throws NotFound {
        if (guild == null) throw new NotFound(Const.GUILD.NOT_FOUND.getMsg());
    }

    public void checkUserInGuild(String guildName) throws Conflict {
        if (!Objects.equals(guildName, "")) throw new Conflict(Const.GUILD.ALREADY_IN_GUILD.getMsg());
    }

    public void checkUserNotInGuild(String guildName) throws Conflict {
        if (Objects.equals(guildName, "")) throw new Conflict(Const.GUILD.NOT_INSIDE.getMsg());
    }

    public void guildNameExist(boolean guildExist) throws Conflict {
        if (guildExist) throw new Conflict(Const.GUILD.NAME_ALREADY_EXIST.getMsg());
    }

    public void guildTagExist(boolean tagExist) throws Conflict {
        if (tagExist) throw new Conflict(Const.GUILD.TAG_ALREADY_EXIST.getMsg());
    }

    public void guildDtoReqToSaveGuild(GuildDTO guildDTO) throws Conflict {
        if (guildDTO.getName() == null) throw new Conflict(Const.GUILD.NAME_REQUIRED.getMsg());
        if (guildDTO.getDescription() == null) throw new Conflict(Const.GUILD.DESCRIPTION_REQUIRED.getMsg());
        if (guildDTO.getTag() == null) throw new Conflict(Const.GUILD.TAG_REQUIRED.getMsg());
    }

    public void guildReqToCreate(int level, long gold, int diamond) throws Conflict {
        if (level < SvConfig.LEVEL_TO_CREATE_GUILD) throw new Conflict(Const.GUILD.LVL_REQ.getMsg());
        if (gold < SvConfig.GOLD_TO_CREATE_GUILD) throw new Conflict(Const.GUILD.GOLD_REQ.getMsg());
        if (diamond < SvConfig.DIAMOND_TO_CREATE_GUILD) throw new Conflict(Const.GUILD.DIAMOND_REQ.getMsg());
    }

    public void reqLvlToReqGuild(int level) throws Conflict {
        if (level < SvConfig.LEVEL_TO_JOIN_GUILD) throw new Conflict(Const.GUILD.LVL_REQ.getMsg());
    }

    public void checkGuildIsFull(int membersSize, int maxMembers) throws Conflict {
        if (membersSize >= maxMembers) throw new Conflict(Const.GUILD.FULL.getMsg());
    }

    public void checkGuildLeaderOrSubLeader(boolean isLeaderOrSubLeader) throws Conflict {
        if (!isLeaderOrSubLeader) throw new Conflict(Const.GUILD.NOT_LEADER_OR_SUBLEADER.getMsg());
    }

    public void checkOtherUserInGuild(String guildName) throws Conflict {
        if (!Objects.equals(guildName, "")) throw new Conflict(Const.GUILD.USER_IN_GUILD.getMsg());
    }

    public void checkUserInReqGuild(boolean userInRequest) throws Conflict {
        if (!userInRequest) throw new Conflict(Const.GUILD.USER_NOT_REQUEST.getMsg());
    }

    public void checkUserIsLeader(String username, String leader) throws Conflict {
        if (Objects.equals(username, leader)) throw new Conflict(Const.GUILD.USER_ALREADY_LEADER.getMsg());
    }

    public void checkUserRemoveLeader(String nameRemove, String leader) throws Conflict {
        if (Objects.equals(nameRemove, leader)) throw new Conflict(Const.GUILD.CANNOT_REMOVE_LEADER.getMsg());
    }

    public void checkRemoveLeaderNotSubLeader(String nameRemove, String leader, String subLeader, int memberSize) throws Conflict {
        if (nameRemove.equals(leader) && subLeader.equals("") && memberSize > 1)
            throw new Conflict(Const.GUILD.NO_SUBLEADER.getMsg());
    }

    public void checkUserDiamondsForDonate(int userDiamonds, int diamonds) throws Conflict {
        if (userDiamonds < diamonds) throw new Conflict(Const.GUILD.YOU_NOT_ENOUGH_DIAMONDS.getMsg());
    }

    public void checkGuildLvlMax(int guildLevel) throws Conflict {
        if (guildLevel >= SvConfig.GUILD_LVL_MAX) throw new Conflict(Const.GUILD.LVL_MAX.getMsg());
    }

    public void checkGuildDiamondsForUpgrade(int diamonds, int lvl) throws Conflict {
        if (diamonds < GuildUpgrade.getDiamondCost(lvl)) throw new Conflict(Const.GUILD.NOT_ENOUGH_DIAMONDS.getMsg());
    }
}
