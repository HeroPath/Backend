package com.gianca1994.aowebbackend.resources.user;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: UserController
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User getProfile(@RequestHeader(value = "Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String token
         * @return User user
         */
        return userService.getProfile(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<UserRankingDTO> getRankingAll(@RequestParam("page") int page) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the ranking of all the users.
         * @return ArrayList<User> users
         */
        return userService.getRankingAll(page);
    }

    @PostMapping("/add-skill-points")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User setFreeSkillPoint(@RequestHeader(value = "Authorization") String token,
                                  @RequestBody FreeSkillPointDTO freeSkillPointDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add skill points to the user.
         * @param String token
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User user
         */
        return userService.setFreeSkillPoint(jwtTokenUtil.getUsernameFromToken(token.substring(7)), freeSkillPointDTO);
    }

    @PostMapping("/attack-user")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody UserAttackUserDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to attack another user.
         * @param String token
         * @param UserAttackUserDTO nameRequestDTO
         * @return ArrayList<ObjectNode> objectNodes
         */
        return userService.userVsUserCombatSystem(jwtTokenUtil.getUsernameFromToken(token.substring(7)), nameRequestDTO);
    }

    @PostMapping("/attack-npc")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody UserAttackNpcDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to attack a npc.
         * @param String token
         * @param UserAttackNpcDTO nameRequestDTO
         * @return ArrayList<ObjectNode> objectNodes
         */
        return userService.userVsNpcCombatSystem(jwtTokenUtil.getUsernameFromToken(token.substring(7)), nameRequestDTO);
    }
}
