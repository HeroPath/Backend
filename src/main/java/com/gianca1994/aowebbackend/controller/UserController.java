package com.gianca1994.aowebbackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.dto.UserAttackNpcDTO;
import com.gianca1994.aowebbackend.exception.ConflictException;
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

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User getProfile(@RequestHeader(value = "Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the profile of the user.
         * @param String token
         * @return User user
         */
        return userService.getProfile(token);
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<User> getRankingAll() {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get the ranking of all users.
         * @param String token
         * @return ArrayList<User> users
         */
        return userService.getRankingAll();
    }

    @PostMapping("/add-skill-points")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User setFreeSkillPoint(@RequestHeader(value = "Authorization") String token,
                                  @RequestBody FreeSkillPointDTO freeSkillPointDTO) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add skill points to the user.
         * @param String token
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User user
         */
        return userService.setFreeSkillPoint(token, freeSkillPointDTO);
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
        return userService.userVsUserCombatSystem(token, usernameDefender);

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
        return userService.userVsNpcCombatSystem(token, userAttackNpcDTO);
    }
}
