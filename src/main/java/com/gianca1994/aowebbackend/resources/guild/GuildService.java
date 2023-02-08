package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.config.GuildUpgradeConfig;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.GuildRankingDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.GuildUserDTO;
import com.gianca1994.aowebbackend.resources.item.ItemConst;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GuildService {

    GuildServiceValidator validator = new GuildServiceValidator();
    private final GuildRankingDTO guildRankingDTO = new GuildRankingDTO();

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<GuildRankingDTO> getAll() {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a list of all guilds
         * @return List<ObjectNode>
         */
        return guildRepository.findAllByOrderByTitlePointsDesc().stream()
                .map(guildRankingDTO::guildRankingDTO)
                .collect(Collectors.toList());
    }

    public GuildUserDTO getUser(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a guild by a user
         * @param String username
         * @return ObjectNode
         */
        User userInGuild = userRepository.findByUsername(username);
        if (userInGuild == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        GuildUserDTO guildUserDTO = new GuildUserDTO(!Objects.equals(userInGuild.getGuildName(), ""));
        Guild guild = guildRepository.findByName(userInGuild.getGuildName());
        if (guild == null) return guildUserDTO;

        guildUserDTO.updateDTO(userInGuild.getUsername(), guild.getName(), guild.getTag(),
                guild.getDescription(), guild.getLeader(), guild.getSubLeader(), guild.getMembers().size(),
                guild.getLevel(), guild.getDiamonds(), guild.getTitlePoints(), SvConfig.MAX_MEMBERS_IN_GUILD
        );

        guildUserDTO.createMembersList(guild, userService);
        guildUserDTO.createRequestList(guild, userService);
        return guildUserDTO;
    }

    public void save(String username, GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method saves a guild
         * @param String username
         * @param GuildDTO guildDTO
         * @return void
         */
        User user = userRepository.findByUsername(username);
        String guildDtoName = guildDTO.getName().toLowerCase();
        String guildDtoTag = guildDTO.getTag().toLowerCase();
        validator.saveGuild(
                user, guildDTO,
                guildRepository.existsGuildByName(guildDtoName),
                guildRepository.existsGuildByTag(guildDtoTag)
        );

        Guild guild = new Guild(
                guildDtoName, guildDTO.getDescription(), guildDtoTag,
                user.getUsername(), (short) 1, 0
        );

        guild.getMembers().add(user);
        guild.setTitlePoints(user.getTitlePoints());

        user.userCreateGuild(guildDtoName, SvConfig.GOLD_TO_CREATE_GUILD, SvConfig.DIAMOND_TO_CREATE_GUILD);
        userRepository.save(user);
        guildRepository.save(guild);
    }

    public void requestUser(String username, String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method adds a user to a guild
         * @param String username
         * @param String guildName
         * @return void
         */
        User user = userRepository.findByUsername(username);
        Guild guild = guildRepository.findByName(guildName);
        validator.requestUserGuild(user, guild);

        guild.getRequests().add(user);
        guildRepository.save(guild);
    }

    public void acceptUser(String username, String nameAccept) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method accepts a user to a guild
         * @param String username
         * @param String nameAccept
         * @return void
         */
        User user = userRepository.findByUsername(username);
        Guild guild = guildRepository.findByName(user.getGuildName());
        User userAccept = userRepository.findByUsername(nameAccept);
        validator.acceptUserGuild(user, guild, userAccept);

        guild.getRequests().remove(userAccept);
        guild.getMembers().add(userAccept);
        guild.setTitlePoints(guild.getTitlePoints() + userAccept.getTitlePoints());
        userAccept.setGuildName(guild.getName());

        userRepository.save(userAccept);
        guildRepository.save(guild);
    }

    public void rejectUser(String username, String nameReject) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method rejects a user to a guild
         * @param String username
         * @param String nameReject
         * @return void
         */
    }

    public void makeUserSubLeader(String username, String nameSubLeader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method makes a user subleader
         * @param String username
         * @param String nameSubLeader
         * @return void
         */
        User user = userRepository.findByUsername(username);
        Guild guild = guildRepository.findByName(user.getGuildName());
        User userSubLeader = userRepository.findByUsername(nameSubLeader);
        validator.makeUserSubLeader(user, guild, userSubLeader);

        if (Objects.equals(userSubLeader.getUsername(), guild.getSubLeader())) guild.setSubLeader("");
        else guild.setSubLeader(userSubLeader.getUsername());

        guildRepository.save(guild);
    }

    public void removeUser(String username, String nameRemove) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method removes a user from a guild
         * @param String username
         * @param String nameRemove
         * @return void
         */
        User user = userRepository.findByUsername(username);
        Guild guild = guildRepository.findByName(user.getGuildName());
        User userRemove = userRepository.findByUsername(nameRemove);
        validator.removeUserGuild(user, guild, userRemove, nameRemove);

        if (userRemove.getUsername().equals(guild.getSubLeader())) guild.setSubLeader("");
        if (Objects.equals(userRemove.getUsername(), guild.getLeader())) {
            guild.setLeader(guild.getSubLeader());
            guild.setSubLeader("");
        }

        guild.getMembers().remove(userRemove);
        guild.setTitlePoints(guild.getTitlePoints() - userRemove.getTitlePoints());
        userRemove.setGuildName("");
        userRepository.save(userRemove);

        if (guild.getMembers().size() == 0) guildRepository.delete(guild);
        else guildRepository.save(guild);
    }

    @Transactional
    public int donateDiamonds(long userId, int diamonds) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method donates diamonds to a guild
         * @param long userId
         * @param int diamonds
         * @return int
         */
        String guildName = userRepository.findGuildNameByUserId(userId);
        if (Objects.equals(guildName, "")) throw new Conflict("You are not in a guild");

        int guildDiamonds = guildRepository.findDiamondsByName(guildName);
        int userDiamonds = userRepository.findDiamondByUserId(userId);
        if (userDiamonds < diamonds) throw new Conflict("You don't have enough diamonds");

        userDiamonds -= diamonds;
        guildDiamonds += diamonds;
        userRepository.updateUserDiamond(userDiamonds, userId);
        guildRepository.updateDiamondsByName(guildDiamonds, guildName);
        return guildDiamonds;
    }

    @Transactional
    public void upgradeLevel(long userId, String username) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method upgrades the level of a guild
         * @param long userId
         * @param String username
         * @return void
         */
        String guildName = userRepository.findGuildNameByUserId(userId);
        if (Objects.equals(guildName, "")) throw new Conflict("You are not in a guild");
        if (!guildRepository.isLeaderOrSubLeader(username, guildName))
            throw new Conflict("You are not the leader or subleader of your guild");

        int guildLevel = guildRepository.findLevelByName(guildName);
        if (guildLevel >= SvConfig.GUILD_LVL_MAX) throw new Conflict("Your guild is already at the maximum level");

        int guildDiamonds = guildRepository.findDiamondsByName(guildName);
        if (guildDiamonds < GuildUpgradeConfig.getDiamondCost(guildLevel))
            throw new Conflict("Your guild doesn't have enough diamonds");

        guildDiamonds -= GuildUpgradeConfig.getDiamondCost(guildLevel);
        guildLevel++;
        guildRepository.updateDiamondsByName(guildDiamonds, guildName);
        guildRepository.updateLevelByName((short) guildLevel, guildName);
    }
}
