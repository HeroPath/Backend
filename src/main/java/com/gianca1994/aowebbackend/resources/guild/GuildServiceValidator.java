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

    public void userFound(User user) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if a user exists
         * @param User user
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
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


    ////////////////////////////////////////////////////////////////////////
    public void requestUserGuild(User user, Guild guild) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method requests a user to join a guild
         * @param User user
         * @param Guild guild
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (!Objects.equals(user.getGuildName(), "")) throw new Conflict("You are already in a guild");
        if (user.getLevel() < SvConfig.LEVEL_TO_JOIN_GUILD)
            throw new Conflict("You need to be level " + SvConfig.LEVEL_TO_JOIN_GUILD + " to join a guild");
        if (guild == null) throw new NotFound("Guild not found");
        if (guild.getMembers().size() >= SvConfig.MAX_MEMBERS_IN_GUILD)
            throw new Conflict("Guild is full");
    }

    public void acceptUserGuild(boolean userExist, String guildName, boolean isLeaderOrSubLeader,
                                Guild guild, User userAccept) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method accepts a user to join a guild
         * @param User user
         * @param Guild guild
         * @param User userAccept
         * @return void
         */
        if (!userExist) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (Objects.equals(guildName, "")) throw new Conflict("You are not in a guild");
        if (!isLeaderOrSubLeader) throw new Conflict("You are not the leader or subleader of the guild");
        if (guild == null) throw new NotFound("Guild not found");
        if (guild.getMembers().size() >= SvConfig.MAX_MEMBERS_IN_GUILD)
            throw new Conflict("Guild is full");
        if (userAccept == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (!Objects.equals(userAccept.getGuildName(), "")) throw new Conflict("User is already in a guild");
        if (!guild.getRequests().contains(userAccept)) throw new Conflict("User is not in the guild requests");
    }

    public void makeUserSubLeader(User user, Guild guild, User userSubLeader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method accepts a user to join a guild
         * @param User user
         * @param Guild guild
         * @param User userSubLeader
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");
        if (guild == null) throw new NotFound("Guild not found");
        if (!Objects.equals(user.getUsername(), guild.getLeader()))
            throw new Conflict("You do not have the permissions to make a subleader");
        if (userSubLeader == null) throw new NotFound("User not found");
        if (Objects.equals(userSubLeader.getUsername(), guild.getLeader()))
            throw new Conflict("User is already leader");
    }

    public void removeUserGuild(User user, Guild guild, User userRemove, String nameRemove) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method removes a user from a guild
         * @param User user
         * @param Guild guild
         * @param User userRemove
         * @param String nameRemove
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");
        if (guild == null) throw new NotFound("Guild not found");

        if (!Objects.equals(nameRemove, user.getUsername())) {
            if (!Objects.equals(nameRemove, user.getUsername()) &&
                    !Objects.equals(user.getUsername(), guild.getLeader()) &&
                    !Objects.equals(user.getUsername(), guild.getSubLeader())
            ) throw new Conflict("You do not have the permissions to delete another member");

            if (Objects.equals(nameRemove, guild.getLeader())) throw new Conflict("You cannot remove the guild leader");

            if (Objects.equals(nameRemove, guild.getSubLeader()) && !Objects.equals(user.getUsername(), guild.getLeader())
            ) throw new Conflict("You cannot remove the guild subleader");
        }
        if (nameRemove.equals(guild.getLeader()) &&
                guild.getSubLeader().equals("") &&
                guild.getMembers().size() > 1
        ) throw new Conflict("You cannot leave the clan because there is no sub-leader to take command");
        if (userRemove == null) throw new NotFound("User not found");
    }

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
