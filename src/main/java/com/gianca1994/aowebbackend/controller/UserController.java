package com.gianca1994.aowebbackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.dto.UserAttackNpcDTO;
import com.gianca1994.aowebbackend.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String getTokenUser(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the username from the token.
         * @param String token
         * @return String username
         */
        String jwtToken = token.substring(7);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User getProfile(@RequestHeader(value = "Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the profile of the user.
         * @param String token
         * @return User user
         */
        if (token != null && token.startsWith("Bearer ")) {
            return userService.getProfile(getTokenUser(token));
        }
        return null;
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<User> getRankingAll(@RequestHeader(value = "Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the ranking of all users.
         * @param String token
         * @return ArrayList<User> users
         */
        if (token != null && token.startsWith("Bearer ")) {
            return userService.getRankingAll();
        }
        return null;
    }

    @PostMapping("/add-skill-points")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User setFreeSkillPoint(@RequestHeader(value = "Authorization") String token,
                                  @RequestBody FreeSkillPointDTO freeSkillPointDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add skill points to the user.
         * @param String token
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User user
         */

        if (token != null && token.startsWith("Bearer ")) {
            return userService.setFreeSkillPoint(getTokenUser(token), freeSkillPointDTO);
        }
        return null;
    }


    //////////////////////////////////////////////////////////////////////
    ////////////////// INFO: PVP AND PVE SYSTEMS /////////////////////////
    //////////////////////////////////////////////////////////////////////

    @PostMapping("/attack-user/{usernameDefender}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String usernameDefender) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to attack a user.
         * @param String token
         * @param String usernameDefender
         * @return ArrayList<ObjectNode> objectNodes
         */
        if (token != null && token.startsWith("Bearer ")) {
            return userService.userVsUserCombatSystem(getTokenUser(token), usernameDefender);
        }
        return null;
    }

    @PostMapping("/attack-npc")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody UserAttackNpcDTO userAttackNpcDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to attack a user.
         * @param String token
         * @param UserAttackNpcDTO userAttackNpcDTO
         * @return ArrayList<ObjectNode> objectNodes
         */
        if (token != null && token.startsWith("Bearer ")) {
            return userService.userVsNpcCombatSystem(getTokenUser(token), userAttackNpcDTO);
        }
        return null;
    }
}
