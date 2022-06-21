package com.gianca1994.aowebbackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
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
        String jwtToken = token.substring(7);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User getProfile(@RequestHeader(value = "Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return userService.getProfile(getTokenUser(token));
        }
        return null;
    }

    @GetMapping("/ranking")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<User> getRankingAll(@RequestHeader(value = "Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return userService.getRankingAll();
        }
        return null;
    }

    @PostMapping("/add-skill-points")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User setFreeSkillPoint(@RequestHeader(value = "Authorization") String token,
                                   @RequestBody FreeSkillPointDTO freeSkillPointDTO) {

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
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token, @PathVariable String usernameDefender) {
        if (token != null && token.startsWith("Bearer ")) {
            return userService.userVsUserCombatSystem(getTokenUser(token), usernameDefender);
        }
        return null;
    }

    @PostMapping("/attack-npc/{npcId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token, @PathVariable long npcId) {
        if (token != null && token.startsWith("Bearer ")) {
            return userService.userVsNpcCombatSystem(getTokenUser(token), npcId);
        }
        return null;
    }
}
