package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDonateDiamondsDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.request.RequestGuildNameDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.RankingDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.UpgradeDonateDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.UserDTO;
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
    public List<RankingDTO> getAllGuilds() throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns a list of all guilds
         * @return List<GuildRankingDTO> - List of all the guilds
         */
        try {
            return guildService.getAll();
        } catch (Exception e) {
            throw new Conflict("Error while getting all the guilds");
        }
    }

    @GetMapping("/in-guild")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UserDTO getUserGuild(@RequestHeader("Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns the guild of the user
         * @param token - Token of the user that is trying to get the guild
         * @return GuildUserDTO - Guild of the user
         */
        return guildService.getUser(
                jwtTokenUtil.getUsernameFromToken(token.substring(7))
        );
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void saveGuild(@RequestHeader("Authorization") String token,
                          @RequestBody GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method saves a guild in the database
         * @param token - Token of the user that is trying to save the guild
         * @param guildDTO - Guild to be saved
         * @return void
         */
        guildService.save(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                guildDTO
        );
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void requestUserGuild(@RequestHeader("Authorization") String token,
                                 @RequestBody RequestGuildNameDTO requestGuildNameDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method adds a user to a guild
         * @param token - Token of the user that is trying to add the user to the guild
         * @param requestGuildNameDTO - Name of the guild to be added
         * @return void
         */
        guildService.requestUser(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                requestGuildNameDTO.getName()
        );
    }

    @GetMapping("/accept/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void acceptUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method accepts a user to a guild
         * @param String token - Token of the user that is trying to accept the user to the guild
         * @param String name - Name of the user to be accepted
         * @return void
         */
        guildService.acceptUser(
                jwtTokenUtil.getIdFromToken(token.substring(7)),
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                name);
    }

    @GetMapping("/reject/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void rejectUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method rejects a user to a guild
         * @param String token - Token of the user that is trying to reject the user to the guild
         * @param String name - Name of the user to be rejected
         * @return void
         */
        guildService.rejectUser(
                jwtTokenUtil.getIdFromToken(token.substring(7)),
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                name);
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

    @GetMapping("/remove/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void removeUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method removes a user from a guild
         * @param token - Token of the user that is trying to remove the user from the guild
         * @param name - Name of the user to be removed
         * @return void
         */
        guildService.removeUser(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                name
        );
    }

    @PostMapping("/donate")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UpgradeDonateDTO donateGuild(@RequestHeader("Authorization") String token,
                                        @RequestBody GuildDonateDiamondsDTO guildDonateDiamondsDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method donates diamonds to a guild
         * @param token - Token of the user that is trying to donate diamonds to the guild
         * @param guildDonateDiamondsDTO - Amount of diamonds to be donated
         * @return int
         */
        return guildService.donateDiamonds(
                jwtTokenUtil.getIdFromToken(token.substring(7)),
                guildDonateDiamondsDTO.getAmountDiamonds()
        );
    }

    @GetMapping("/upgrade")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UpgradeDonateDTO upgradeGuild(@RequestHeader("Authorization") String token) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method upgrades the level of a guild
         * @param token - Token of the user that is trying to upgrade the level of the guild
         * @return void
         */
        return guildService.upgradeLevel(
                jwtTokenUtil.getIdFromToken(token.substring(7)),
                jwtTokenUtil.getUsernameFromToken(token.substring(7))
        );
    }
}
