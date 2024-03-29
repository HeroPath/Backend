package com.gianca1994.heropathbackend.resources.guild;

import com.gianca1994.heropathbackend.utils.GuildUpgrade;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.heropathbackend.resources.guild.dto.response.RankingDTO;
import com.gianca1994.heropathbackend.resources.guild.dto.response.UpgradeDonateDTO;
import com.gianca1994.heropathbackend.resources.guild.dto.response.UserDTO;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.resources.user.UserService;
import com.gianca1994.heropathbackend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the methods to manage the guilds
 */

@Service
public class GuildService {

    Validator validate = new Validator();
    private final RankingDTO rankingDTO = new RankingDTO();

    @Autowired
    private GuildRepository guildR;

    @Autowired
    private UserRepository userR;

    @Autowired
    private UserService userS;

    public List<RankingDTO> getAll() {
        /**
         * @Author: Gianca1994
         * @Explanation: This method returns a list of all guilds
         * @return List<ObjectNode>
         */
        return guildR.findAllByOrderByTitlePointsDesc().stream()
                .map(rankingDTO::guildRankingDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(long userId, String username) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method returns a userDTO
         * @param long userId
         * @param String username
         * @return UserDTO
         */
        validate.userExist(userR.existsById(userId));

        User userInGuild = userR.findByUsername(username);
        UserDTO userDTO = new UserDTO(!Objects.equals(userInGuild.getGuildName(), ""));
        Guild guild = guildR.findByName(userInGuild.getGuildName());
        if (guild == null) return userDTO;

        userDTO.updateDTO(userInGuild.getUsername(), guild.getName(), guild.getTag(), guild.getDescription(),
                guild.getLeader(), guild.getSubLeader(), guild.getMembers().size(),
                guild.getLevel(), guild.getDiamonds(), guild.getTitlePoints(),
                guild.getMaxMembers()
        );

        userDTO.createMembersList(guild, userS);
        userDTO.createRequestList(guild, userS);
        return userDTO;
    }

    public void save(long userId, String username, GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method creates a guild
         * @param long userId
         * @param String username
         * @param GuildDTO guildDTO
         * @return void
         */
        validate.guildDtoReqToSaveGuild(guildDTO);
        validate.userExist(userR.existsById(userId));

        User user = userR.findByUsername(username);
        validate.userInGuild(user.getGuildName());

        String guildDtoName = guildDTO.getName().toLowerCase();
        validate.guildNameExist(guildR.existsGuildByName(guildDtoName));

        String guildDtoTag = guildDTO.getTag().toLowerCase();
        validate.guildTagExist(guildR.existsGuildByTag(guildDtoTag));

        validate.guildReqToCreate(user.getLevel(), user.getGold(), user.getDiamond());
        Guild guild = new Guild(guildDtoName, guildDTO.getDescription(), guildDtoTag, user.getUsername(), user.getTitlePoints(), user);

        user.userCreateGuild(guildDtoName, SvConfig.GOLD_TO_CREATE_GUILD, SvConfig.DIAMOND_TO_CREATE_GUILD);
        userR.save(user);
        guildR.save(guild);
    }

    public void requestUser(long userId, String username, String guildName) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method requests a user to a guild
         * @param long userId
         * @param String username
         * @param String guildName
         * @return void
         */
        validate.userExist(userR.existsById(userId));

        User user = userR.findByUsername(username);
        validate.userInGuild(user.getGuildName());
        validate.reqLvlToReqGuild(user.getLevel());

        Guild guild = guildR.findByName(guildName);
        validate.guildExistByObject(guild);
        validate.guildIsFull(guild.getMembers().size(), guild.getMaxMembers());

        guild.getRequests().add(user);
        guildR.save(guild);
    }

    public void acceptUser(long userId, String username, String nameAccept) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method accepts a user to a guild
         * @param long userId
         * @param String username
         * @param String nameAccept
         * @return void
         */
        String guildName = getGuildName(userId);
        validate.guildLeaderOrSubLeader(guildR.isLeaderOrSubLeader(username, guildName));

        Guild guild = guildR.findByName(guildName);
        validate.guildIsFull(guild.getMembers().size(), guild.getMaxMembers());

        User userAccept = userR.findByUsername(nameAccept);
        validate.userExistByObject(userAccept);
        validate.otherUserInGuild(userAccept.getGuildName());
        validate.userInReqGuild(guild.getRequests().contains(userAccept));

        guild.userAddGuild(userAccept);
        userAccept.setGuildName(guild.getName());
        userR.save(userAccept);
        guildR.save(guild);
    }

    public void rejectUser(long userId, String username, String nameReject) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method rejects a user to a guild
         * @param long userId
         * @param String username
         * @param String nameReject
         * @return void
         */
        String guildName = getGuildName(userId);
        Guild guild = guildR.findByName(guildName);

        validate.guildLeaderOrSubLeader(guildR.isLeaderOrSubLeader(username, guildName));
        validate.userInReqGuild(guild.getRequests().contains(userR.findByUsername(nameReject)));

        guild.getRequests().removeIf(u -> u.getUsername().equals(nameReject));
        guildR.save(guild);
    }

    public void makeUserSubLeader(long userId, String username, String nameNewSubLeader) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method makes a user sub leader
         * @param long userId
         * @param String username
         * @param String nameNewSubLeader
         * @return void
         */
        String guildName = getGuildName(userId);
        validate.guildLeaderOrSubLeader(guildR.isLeaderOrSubLeader(username, guildName));

        Guild guild = guildR.findByName(guildName);
        User userSubLeader = userR.findByUsername(nameNewSubLeader);
        validate.userExistByObject(userSubLeader);
        validate.userIsLeader(userSubLeader.getUsername(), guild.getLeader());

        if (Objects.equals(userSubLeader.getUsername(), guild.getSubLeader())) guild.setSubLeader("");
        else guild.setSubLeader(userSubLeader.getUsername());
        guildR.save(guild);
    }

    public void removeUser(long userId, String username, String nameRemove) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method removes a user from a guild
         * @param long userId
         * @param String username
         * @param String nameRemove
         * @return void
         */
        validate.userExist(userR.existsById(userId));

        User user = userR.findByUsername(username);
        validate.userNotInGuild(user.getGuildName());

        Guild guild = guildR.findByName(user.getGuildName());
        validate.guildExistByObject(guild);
        validate.guildLeaderOrSubLeader(guildR.isLeaderOrSubLeader(username, guild.getName()));

        User userRemove = userR.findByUsername(nameRemove);
        validate.userExistByObject(userRemove);

        if (!Objects.equals(nameRemove, user.getUsername())) {
            validate.guildLeaderOrSubLeader(guildR.isLeaderOrSubLeader(username, guild.getName()));
            validate.userRemoveLeader(userRemove.getUsername(), guild.getLeader());
        }
        validate.removeLeaderNotSubLeader(userRemove.getUsername(), guild.getLeader(), guild.getSubLeader(), guild.getMembers().size());

        guild.userRemoveGuild(userRemove);
        userRemove.setGuildName("");
        userR.save(userRemove);

        if (guild.getMembers().size() <= 0) guildR.delete(guild);
        else guildR.save(guild);
    }

    @Transactional
    public UpgradeDonateDTO donateDiamonds(long userId, int diamonds) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method donates diamonds to a guild
         * @param long userId
         * @param int diamonds
         * @return UpgradeDonateDTO
         */
        String guildName = getGuildName(userId);
        int guildLevel = guildR.findLevelByName(guildName);
        int userDiamonds = userR.findDiamondByUserId(userId);
        validate.userDiamondsDonate(userDiamonds, diamonds);

        userDiamonds -= diamonds;
        int guildDiamonds = guildR.findDiamondsByName(guildName);
        guildDiamonds += diamonds;

        userR.updateUserDiamond(userId, userDiamonds);
        guildR.updateDiamondsByName(guildDiamonds, guildName);
        return new UpgradeDonateDTO(guildLevel, guildDiamonds);
    }

    @Transactional
    public UpgradeDonateDTO upgradeLevel(long userId, String username) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method upgrades the level of a guild
         * @param long userId
         * @param String username
         * @return UpgradeDonateDTO
         */
        String guildName = getGuildName(userId);
        validate.guildLeaderOrSubLeader(guildR.isLeaderOrSubLeader(username, guildName));

        int guildLevel = guildR.findLevelByName(guildName);
        validate.guildLvlMax(guildLevel);
        int guildDiamonds = guildR.findDiamondsByName(guildName);
        validate.guildDiamondsUpgrade(guildDiamonds, guildLevel);
        int guildMaxMembers = guildR.findMaxMembersByName(guildName);

        guildDiamonds -= GuildUpgrade.getDiamondCost(guildLevel);
        guildLevel++;
        guildMaxMembers++;

        guildR.updateDiamondsByName(guildDiamonds, guildName);
        guildR.updateLevelByName((short) guildLevel, guildName);
        guildR.updateMaxMembersByName(guildMaxMembers, guildName);
        return new UpgradeDonateDTO(guildLevel, guildDiamonds);
    }

    private String getGuildName(long userId) throws Conflict {
        validate.userExist(userR.existsById(userId));

        String guildName = userR.findGuildNameByUserId(userId);
        validate.userNotInGuild(guildName);
        validate.guildExist(guildR.existsGuildByName(guildName));

        return guildName;
    }
}
