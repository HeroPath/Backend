package com.gianca1994.heropathbackend.resources.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.heropathbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.heropathbackend.resources.user.dto.response.RankingResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to manage the user's requests.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userS;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User getProfile(@RequestHeader(value = "Authorization") String token) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to get the profile of the user.
         * @param String token
         * @return User
         */
        try {
            return userS.getProfile(
                    jwt.getUsernameFromToken(token.substring(7))
            );
        } catch (Exception e) {
            throw new Conflict("Error in getting the profile");
        }
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public RankingResponseDTO getRankingAll(@RequestParam("page") int page) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to get the ranking of all the users.
         * @param int page
         * @return RankingResponseDTO
         */
        return userS.getRankingAll(page);
    }

    @PostMapping("/add-skill-points/{skillName}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public UserAttributes setFreeSkillPoint(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String skillName) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to add a skill point to a skill.
         * @param String token
         * @param String skillName
         * @return UserAttributes
         */
        return userS.setFreeSkillPoint(
                jwt.getIdFromToken(token.substring(7)), skillName
        );
    }

    @PostMapping("/attack-user")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to attack a user.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        return userS.userVsUserCombatSystem(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName().toLowerCase()
        );
    }

    @PostMapping("/attack-npc")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackNpc(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to attack a npc.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return ArrayList<ObjectNode>
         */
        return userS.userVsNpcCombatSystem(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName().toLowerCase()
        );
    }
}
