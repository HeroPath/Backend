package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.item.ItemConst;
import com.gianca1994.aowebbackend.resources.user.User;

import java.util.Objects;

public class GuildServiceValidator {

    public void saveGuild(User user, GuildDTO guildDTO, boolean nameExist, boolean tagExist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method creates a guild
         * @param User user
         * @param GuildDTO guildDTO
         * @param Guild checkGuild
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (!Objects.equals(user.getGuildName(), "")) throw new Conflict("You are already in a guild");
        if (user.getLevel() < SvConfig.LEVEL_TO_CREATE_GUILD)
            throw new Conflict("You need to be level " + SvConfig.LEVEL_TO_CREATE_GUILD + " to create a guild");
        if (user.getGold() < SvConfig.GOLD_TO_CREATE_GUILD)
            throw new Conflict("You need " + SvConfig.GOLD_TO_CREATE_GUILD + " gold to create a guild");
        if (user.getDiamond() < SvConfig.DIAMOND_TO_CREATE_GUILD)
            throw new Conflict("You need " + SvConfig.DIAMOND_TO_CREATE_GUILD + " diamonds to create a guild");

        if (guildDTO.getName() == null) throw new Conflict("Name is required");
        if (guildDTO.getDescription() == null) throw new Conflict("Description is required");
        if (guildDTO.getTag() == null) throw new Conflict("Tag is required");
        if (nameExist) throw new Conflict("Guild already exists");
        if (tagExist) throw new Conflict("Tag already exists");
    }

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

    public void acceptUserGuild(User user, Guild guild, User userAccept) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method accepts a user to join a guild
         * @param User user
         * @param Guild guild
         * @param User userAccept
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");
        if (guild == null) throw new NotFound("Guild not found");
        if (!Objects.equals(user.getUsername(), guild.getLeader()) && !Objects.equals(user.getUsername(), guild.getSubLeader()))
            throw new Conflict("You are not the leader or subleader of the guild");
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


}
