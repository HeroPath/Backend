package com.gianca1994.aowebbackend.resources.guild;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
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
        return guildsNode;
    }

    public List<ObjectNode> getAllGuilds() {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a list of all guilds
         * @return List<ObjectNode>
         */
        List<Guild> guilds = guildRepository.findAll();
        return guilds.stream().map(this::guildToObjectNode).collect(Collectors.toList());
    }

    public ObjectNode getGuildByName(String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a guild by its name
         * @param String name
         * @return ObjectNode
         */
        Guild guild = guildRepository.findByName(name.toLowerCase());
        if (guild == null) throw new NotFound("Guild not found");

        ObjectNode guildNode = guildToObjectNode(guild);
        guildNode.putPOJO("members", guild.getMembers().stream().map(user -> userService.getUserForGuild(user.getUsername())).collect(Collectors.toList()));
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

        if (guildDTO.getName() == null) throw new Conflict("Name is required");
        if (guildDTO.getDescription() == null) throw new Conflict("Description is required");
        if (guildDTO.getTag() == null) throw new Conflict("Tag is required");

        Guild checkGuild = guildRepository.findByName(guildDTO.getName());
        if (checkGuild != null) throw new Conflict("Guild already exists");

        Guild guild = new Guild();

        guild.setName(guildDTO.getName().toLowerCase());
        guild.setDescription(guildDTO.getDescription());
        guild.setTag(guildDTO.getTag());
        guild.setLeader(user.getUsername());
        guild.setSubLeader("");
        guild.getMembers().add(user);

        user.setGuildName(guildDTO.getName());

        userService.updateUser(user);
        guildRepository.save(guild);
    }

    public void addUserGuild(String username, String guildName) throws Conflict {
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

        Guild guild = guildRepository.findByName(guildName.toLowerCase());
        if (guild == null) throw new NotFound("Guild not found");

        guild.getMembers().add(user);
        user.setGuildName(guild.getName());

        userService.updateUser(user);
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
        if (user.getUsername().equals(nameRemove)) throw new Conflict("You can't remove yourself from the guild");

        if (Objects.equals(user.getGuildName(), "")) throw new Conflict("You are not in a guild");

        Guild guild = guildRepository.findByName(user.getGuildName().toLowerCase());
        if (guild == null) throw new NotFound("Guild not found");

        if (!Objects.equals(user.getUsername(), guild.getLeader()) && !Objects.equals(user.getUsername(), guild.getSubLeader()))
            throw new Conflict("You are not the leader or subleader of the guild");

        User userRemove = userRepository.findByUsername(nameRemove);

        if (userRemove == null) throw new NotFound("User not found");
        if (userRemove.getUsername().equals(guild.getLeader()))
            throw new Conflict("You can't remove the leader of the guild");

        guild.getMembers().remove(userRemove);
        userRemove.setGuildName("");

        userService.updateUser(userRemove);
        guildRepository.save(guild);
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

        Guild guild = guildRepository.findByName(userInGuild.getGuildName().toLowerCase());

        if (guild == null) return guildNode;

        guildNode.put("name", guild.getName());
        guildNode.put("tag", guild.getTag());
        guildNode.put("description", guild.getDescription());
        guildNode.put("leader", guild.getLeader());
        guildNode.put("subLeader", guild.getSubLeader());
        guildNode.put("memberAmount", guild.getMembers().size());
        guildNode.putPOJO("members", guild.getMembers().stream().map(user -> userService.getUserForGuild(user.getUsername())).collect(Collectors.toList()));

        return guildNode;
    }
}
