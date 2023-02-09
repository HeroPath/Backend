package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.config.GuildUpgradeConfig;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.RankingDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.UpgradeDonateDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.UserDTO;
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
    private final RankingDTO rankingDTO = new RankingDTO();

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<RankingDTO> getAll() {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a list of all guilds
         * @return List<ObjectNode>
         */
        return guildRepository.findAllByOrderByTitlePointsDesc().stream()
                .map(rankingDTO::guildRankingDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a guild by a user
         * @param String username
         * @return ObjectNode
         */
        User userInGuild = userRepository.findByUsername(username);
        if (userInGuild == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        UserDTO userDTO = new UserDTO(!Objects.equals(userInGuild.getGuildName(), ""));
        Guild guild = guildRepository.findByName(userInGuild.getGuildName());
        if (guild == null) return userDTO;

        userDTO.updateDTO(userInGuild.getUsername(), guild.getName(), guild.getTag(),
                guild.getDescription(), guild.getLeader(), guild.getSubLeader(), guild.getMembers().size(),
                guild.getLevel(), guild.getDiamonds(), guild.getTitlePoints(), SvConfig.MAX_MEMBERS_IN_GUILD
        );

        userDTO.createMembersList(guild, userService);
        userDTO.createRequestList(guild, userService);
        return userDTO;
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
        validator.saveGuild(user, guildDTO, guildRepository.existsGuildByName(guildDtoName), guildRepository.existsGuildByTag(guildDtoTag));

        Guild guild = new Guild(guildDtoName, guildDTO.getDescription(), guildDtoTag, user.getUsername(), user.getTitlePoints(), user);

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

        guild.userAddGuild(userAccept);
        userAccept.setGuildName(guild.getName());

        userRepository.save(userAccept);
        guildRepository.save(guild);
    }

    public void rejectUser(long userId, String username, String nameReject) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method rejects a user to a guild
         * @param long userId - Id of the user who rejects
         * @param String username - Username of the user who rejects
         * @param String nameReject - Username of the user who is rejected
         * @return void
         */
        String guildName = userRepository.findGuildNameByUserId(userId);
        if (guildName.equals("")) throw new Conflict("You are not in a guild");

        Guild guild = guildRepository.findByName(guildName);
        if (guild == null) throw new NotFound("Guild not found");

        if (!guildRepository.isLeaderOrSubLeader(username, guildName))
            throw new Conflict("You are not the leader or subleader of this guild");
        if (guild.getRequests().stream().noneMatch(u -> u.getUsername().equals(nameReject)))
            throw new Conflict("User is not a request for this guild");

        guild.getRequests().removeIf(u -> u.getUsername().equals(nameReject));
        guildRepository.save(guild);
    }

    public void makeUserSubLeader(String username, String nameSubLeader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method makes a user subLeader
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

        guild.userRemoveGuild(userRemove);
        userRemove.setGuildName("");
        userRepository.save(userRemove);

        if (guild.getMembers().size() == 0) guildRepository.delete(guild);
        else guildRepository.save(guild);
    }

    @Transactional
    public UpgradeDonateDTO donateDiamonds(long userId, int diamonds) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method donates diamonds to a guild
         * @param long userId
         * @param int diamonds
         * @return int
         */
        String guildName = userRepository.findGuildNameByUserId(userId);
        int guildLevel = guildRepository.findLevelByName(guildName);
        int userDiamonds = userRepository.findDiamondByUserId(userId);
        validator.donateDiamonds(guildName, diamonds, userDiamonds);

        userDiamonds -= diamonds;
        int guildDiamonds = guildRepository.findDiamondsByName(guildName);
        guildDiamonds += diamonds;

        userRepository.updateUserDiamond(userDiamonds, userId);
        guildRepository.updateDiamondsByName(guildDiamonds, guildName);
        return new UpgradeDonateDTO(guildLevel, guildDiamonds);
    }

    @Transactional
    public UpgradeDonateDTO upgradeLevel(long userId, String username) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method upgrades the level of a guild
         * @param long userId
         * @param String username
         * @return void
         */
        String guildName = userRepository.findGuildNameByUserId(userId);
        boolean isLeaderOrSubLeader = guildRepository.isLeaderOrSubLeader(username, guildName);
        int guildLevel = guildRepository.findLevelByName(guildName);
        int guildDiamonds = guildRepository.findDiamondsByName(guildName);

        validator.upgradeLevel(guildName, isLeaderOrSubLeader, guildLevel, guildDiamonds);

        guildDiamonds -= GuildUpgradeConfig.getDiamondCost(guildLevel);
        guildLevel++;
        guildRepository.updateDiamondsByName(guildDiamonds, guildName);
        guildRepository.updateLevelByName((short) guildLevel, guildName);
        return new UpgradeDonateDTO(guildLevel, guildDiamonds);
    }
}
