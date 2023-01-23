package com.gianca1994.aowebbackend.resources.guild;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/guilds")
public class GuildController {

    @Autowired
    private GuildService guildService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

    @GetMapping("/in-guild")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ObjectNode getUserGuild(@RequestHeader("Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns the guild of the user
         * @param token - Token of the user that is trying to get the guild
         * @return ObjectNode - Guild of the user
         */
        return guildService.getUserGuild(
                jwtTokenUtil.getUsernameFromToken(token.substring(7))
        );

    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void saveGuild(@RequestHeader("Authorization") String token,
                          @RequestBody GuildDTO guildDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This method saves a guild in the database
         * @param token - Token of the user that is trying to save the guild
         * @return void
         */
        try {
            guildService.saveGuild(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    guildDTO
            );
        } catch (Conflict e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void requestUserGuild(@RequestHeader("Authorization") String token,
                                 @RequestBody RequestGuildNameDTO requestGuildNameDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This method adds a user to a guild
         * @param token - Token of the user that is trying to add the user to the guild
         * @return void
         */
        try {
            guildService.requestUserGuild(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    requestGuildNameDTO.getName()
            );
        } catch (Conflict e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/accept/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void acceptUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This method accepts a user to a guild
         * @param String token - Token of the user that is trying to accept the user to the guild
         * @param String name - Name of the user to be accepted
         * @return void
         */
        try {
            guildService.acceptUserGuild(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    name);
        } catch (Conflict e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/remove/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void removeUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This method removes a user from a guild
         * @param token - Token of the user that is trying to remove the user from the guild
         * @return void
         */
        try {
            guildService.removeUserGuild(
                    jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                    name
            );
        } catch (Conflict e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/make-subleader/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void makeUserSubLeader(@RequestHeader("Authorization") String token,
                                  @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method makes a user subleader of a guild
         * @param token - Token of the user that is trying to make the user subleader
         * @param name - Name of the user to be made subleader
         * @return void
         */
        guildService.makeUserSubLeader(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)), name);
    }

}
