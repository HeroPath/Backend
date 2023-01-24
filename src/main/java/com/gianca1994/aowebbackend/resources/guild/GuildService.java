package com.gianca1994.aowebbackend.resources.guild;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.item.ItemConst;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GuildService {

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

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

    public List<ObjectNode> getAllGuilds() {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a list of all guilds
         * @return List<ObjectNode>
         */
        List<Guild> guilds = guildRepository.findAll();
        return guilds.stream()
                .sorted(Comparator.comparingInt(Guild::getTitlePoints).reversed())
                .map(this::guildToObjectNode)
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
                        .map(user -> userService.getUserForGuild(user.getUsername()))
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

        guildNode.putPOJO("requests", guild.getRequests().stream().map(user -> userService.getUserForGuild(user.getUsername())).collect(Collectors.toList()));

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

        Guild checkGuild = guildRepository.findByName(guildDTO.getName());
        if (checkGuild != null) throw new Conflict("Guild already exists");

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
        User user = userService.getProfile(username);
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (!Objects.equals(user.getGuildName(), "")) throw new Conflict("You are already in a guild");
        if (user.getLevel() < SvConfig.LEVEL_TO_JOIN_GUILD)
            throw new Conflict("You need to be level " + SvConfig.LEVEL_TO_JOIN_GUILD + " to join a guild");

        Guild guild = guildRepository.findByName(guildName);
        if (guild == null) throw new NotFound("Guild not found");
        if (guild.getMembers().size() >= SvConfig.MAX_MEMBERS_IN_GUILD)
            throw new Conflict("Guild is full");

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
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");

        Guild guild = guildRepository.findByName(user.getGuildName());
        if (guild == null) throw new NotFound("Guild not found");

        if (!Objects.equals(user.getUsername(), guild.getLeader()) && !Objects.equals(user.getUsername(), guild.getSubLeader()))
            throw new Conflict("You are not the leader or subleader of the guild");

        if (guild.getMembers().size() >= SvConfig.MAX_MEMBERS_IN_GUILD)
            throw new Conflict("Guild is full");

        User userAccept = userRepository.findByUsername(nameAccept);
        if (userAccept == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        if (!Objects.equals(userAccept.getGuildName(), "")) throw new Conflict("User is already in a guild");
        if (!guild.getRequests().contains(userAccept)) throw new Conflict("User is not in the guild requests");

        guild.getRequests().remove(userAccept);
        guild.getMembers().add(userAccept);
        guild.setTitlePoints(guild.getTitlePoints() + userAccept.getTitlePoints());

        userAccept.setGuildName(guild.getName());

        userRepository.save(userAccept);
        guildRepository.save(guild);

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
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");

        Guild guild = guildRepository.findByName(user.getGuildName());
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

        User userRemove = userRepository.findByUsername(nameRemove);
        if (userRemove == null) throw new NotFound("User not found");

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
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");

        Guild guild = guildRepository.findByName(user.getGuildName());
        if (guild == null) throw new NotFound("Guild not found");

        if (!Objects.equals(user.getUsername(), guild.getLeader()))
            throw new Conflict("You do not have the permissions to make a subleader");

        User userSubLeader = userRepository.findByUsername(nameSubLeader);
        if (userSubLeader == null) throw new NotFound("User not found");

        if (Objects.equals(userSubLeader.getUsername(), guild.getLeader()))
            throw new Conflict("User is already leader");

        if (Objects.equals(userSubLeader.getUsername(), guild.getSubLeader())) guild.setSubLeader("");
        else guild.setSubLeader(userSubLeader.getUsername());

        guildRepository.save(guild);
    }
}
