package com.gianca1994.aowebbackend.resources.guild;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.GuildRankingDTO;
import com.gianca1994.aowebbackend.resources.item.ItemConst;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GuildService {

    GuildServiceValidator validator = new GuildServiceValidator();
    private GuildRankingDTO guildRankingDTO = new GuildRankingDTO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private ObjectNode guildToObjectNode(Guild guild) {
        /**
         * @Author: Gianca1994
         * Explanation: This method converts a Guild object into a JSON ObjectNode
         * @param guild: Guild object
         * @return ObjectNode
         */
        ObjectNode guildsNode = mapper.createObjectNode();
        guildsNode.put("name", guild.getName());
        guildsNode.put("description", guild.getDescription());
        guildsNode.put("tag", guild.getTag());
        guildsNode.put("leader", guild.getLeader());
        guildsNode.put("subLeader", guild.getSubLeader());
        guildsNode.put("memberAmount", guild.getMembers().size());
        guildsNode.put("maxMembers", SvConfig.MAX_MEMBERS_IN_GUILD);
        guildsNode.put("titlePoints", guild.getTitlePoints());
        return guildsNode;
    }

    public List<GuildRankingDTO> getAllGuilds() {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a list of all guilds
         * @return List<ObjectNode>
         */
        return guildRepository.findAllByOrderByTitlePointsDesc().stream()
                .map(guildRankingDTO::guildRankingDTO)
                .collect(Collectors.toList());
    }

    public ObjectNode getUserGuild(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a guild by a user
         * @param String username
         * @return ObjectNode
         */
        User userInGuild = userRepository.findByUsername(username);
        if (userInGuild == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        ObjectNode guildNode = mapper.createObjectNode();
        guildNode.put("userInGuild", !Objects.equals(userInGuild.getGuildName(), ""));

        Guild guild = guildRepository.findByName(userInGuild.getGuildName());
        if (guild == null) return guildNode;

        guildNode.put("username", userInGuild.getUsername());
        guildNode.put("name", guild.getName());
        guildNode.put("tag", guild.getTag());
        guildNode.put("description", guild.getDescription());
        guildNode.put("leader", guild.getLeader());
        guildNode.put("subLeader", guild.getSubLeader());
        guildNode.put("memberAmount", guild.getMembers().size());
        guildNode.put("level", guild.getLevel());
        guildNode.put("diamonds", guild.getDiamonds());
        guildNode.put("titlePoints", guild.getTitlePoints());
        guildNode.put("maxMembers", SvConfig.MAX_MEMBERS_IN_GUILD);
        guildNode.putPOJO("members",
                guild.getMembers().stream()
                        .map(user -> userService.getUserForGuild(user.getId()))
                        .sorted((user1, user2) -> {
                            if (user1.getUsername().equals(guild.getLeader())) return -1;
                            else if (user2.getUsername().equals(guild.getLeader())) return 1;
                            else if (user1.getUsername().equals(guild.getSubLeader())) return -1;
                            else if (user2.getUsername().equals(guild.getSubLeader())) return 1;
                            else if (user1.getTitlePoints() == user2.getTitlePoints())
                                return -1 * Integer.compare(user1.getLevel(), user2.getLevel());
                            else return -1 * Integer.compare(user1.getTitlePoints(), user2.getTitlePoints());
                        }).collect(Collectors.toList())
        );
        guildNode.putPOJO("requests", guild.getRequests().stream().map(user -> userService.getUserForGuild(user.getId())).collect(Collectors.toList()));
        return guildNode;
    }

    public void saveGuild(String username, GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method saves a guild
         * @param String username
         * @param GuildDTO guildDTO
         * @return void
         */
        User user = userRepository.findByUsername(username);
        Guild checkGuild = guildRepository.findByName(guildDTO.getName());
        validator.saveGuild(user, guildDTO, checkGuild);

        Guild guild = new Guild(
                guildDTO.getName(), guildDTO.getDescription(), guildDTO.getTag(),
                user.getUsername(), (short) 1, 0
        );

        guild.getMembers().add(user);
        guild.setTitlePoints(user.getTitlePoints());

        user.setGuildName(guildDTO.getName());
        user.setGold(user.getGold() - SvConfig.GOLD_TO_CREATE_GUILD);
        user.setDiamond(user.getDiamond() - SvConfig.DIAMOND_TO_CREATE_GUILD);

        userRepository.save(user);
        guildRepository.save(guild);
    }

    public void requestUserGuild(String username, String guildName) throws Conflict {
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

    public void acceptUserGuild(String username, String nameAccept) throws Conflict {
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

    public void rejectUserGuild(String username, String nameReject) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method rejects a user to a guild
         * @param String username
         * @param String nameReject
         * @return void
         */
    }

    public void removeUserGuild(String username, String nameRemove) throws Conflict {
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
}
