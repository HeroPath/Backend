package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.request.GuildDonateDiamondsDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.request.RequestGuildNameDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.RankingDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.UpgradeDonateDTO;
import com.gianca1994.aowebbackend.resources.guild.dto.response.UserDTO;
import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/guilds")
public class GuildController {

    @Autowired
    private GuildService guildS;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<RankingDTO> getAllGuilds() throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns all the guilds in the database
         * @return List<RankingDTO>
         */
        try {
            return guildS.getAll();
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
         * @param String token
         * @return UserDTO
         */
        return guildS.getUser(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7))
        );
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void saveGuild(@RequestHeader("Authorization") String token,
                          @RequestBody GuildDTO guildDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method saves a guild in the database
         * @param String token
         * @param GuildDTO guildDTO
         * @return void
         */
        guildS.save(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7)),
                guildDTO
        );
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void requestUserGuild(@RequestHeader("Authorization") String token,
                                 @RequestBody RequestGuildNameDTO requestGuildNameDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method requests a user to a guild
         * @param String token
         * @param RequestGuildNameDTO requestGuildNameDTO
         * @return void
         */
        guildS.requestUser(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7)),
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
         * @param String token
         * @param String name
         * @return void
         */
        guildS.acceptUser(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7)),
                name);
    }

    @GetMapping("/reject/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void rejectUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method rejects a user to a guild
         * @param String token
         * @param String name
         * @return void
         */
        guildS.rejectUser(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7)),
                name);
    }

    @GetMapping("/make-subleader/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void makeUserSubLeader(@RequestHeader("Authorization") String token,
                                  @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method makes a user subleader
         * @param String token
         * @param String name
         * @return void
         */
        guildS.makeUserSubLeader(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7)),
                name
        );
    }

    @GetMapping("/remove/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void removeUserGuild(@RequestHeader("Authorization") String token,
                                @PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method removes a user from a guild
         * @param String token
         * @param String name
         * @return void
         */
        guildS.removeUser(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7)),
                name
        );
    }

    @PostMapping("/donate")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UpgradeDonateDTO donateGuild(@RequestHeader("Authorization") String token,
                                        @RequestBody GuildDonateDiamondsDTO guildDonateDiamondsDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method donates diamonds to the guild
         * @param String token
         * @param GuildDonateDiamondsDTO guildDonateDiamondsDTO
         * @return UpgradeDonateDTO
         */
        return guildS.donateDiamonds(
                jwt.getIdFromToken(token.substring(7)),
                guildDonateDiamondsDTO.getAmountDiamonds()
        );
    }

    @GetMapping("/upgrade")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UpgradeDonateDTO upgradeGuild(@RequestHeader("Authorization") String token) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method upgrades the guild level
         * @param String token
         * @return UpgradeDonateDTO
         */
        return guildS.upgradeLevel(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7))
        );
    }
}
