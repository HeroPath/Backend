package com.gianca1994.aowebbackend.resources.guild;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/guilds")
public class GuildController {

    @Autowired
    private GuildService guildService;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<ObjectNode> getAllGuilds() {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns all the guilds in the database
         * @return List<ObjectNode> - List of all the guilds in the database
         */
        return guildService.getAllGuilds();
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ObjectNode getGuildByName(@PathVariable String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns the guild with the name passed as parameter
         * @param name - Name of the guild to be returned
         * @return ObjectNode - Guild with the name passed as parameter
         */
        return guildService.getGuildByName(name);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void saveGuild(@RequestHeader("Authorization") String token,
                          @RequestBody GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method saves a guild in the database
         * @param token - Token of the user that is trying to save the guild
         * @return void
         */
        guildService.saveGuild(token, guildDTO);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void addUserGuild(@RequestHeader("Authorization") String token,
                             @RequestBody RequestGuildNameDTO requestGuildNameDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method adds a user to a guild
         * @param token - Token of the user that is trying to add the user to the guild
         * @return void
         */
        guildService.addUserGuild(token, requestGuildNameDTO.getName());
    }

    @GetMapping("/remove/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void removeUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method removes a user from a guild
         * @param token - Token of the user that is trying to remove the user from the guild
         * @return void
         */
        guildService.removeUserGuild(token, name);
    }

}
