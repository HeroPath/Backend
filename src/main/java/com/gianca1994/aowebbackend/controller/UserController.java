package com.gianca1994.aowebbackend.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.dto.*;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
                                  @RequestBody FreeSkillPointDTO freeSkillPointDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add skill points to the user.
         * @param String token
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User user
         */
        return userService.setFreeSkillPoint(token, freeSkillPointDTO);
    }

    @PostMapping("/equip-item")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User equipItem(@RequestHeader(value = "Authorization") String token,
                          @RequestBody EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to equip an item to the user.
         * @param String token
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User user
         */
        return userService.equipItem(token, equipUnequipItemDTO);
    }

    @PostMapping("/unequip-item")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public User unequipItem(@RequestHeader(value = "Authorization") String token,
                            @RequestBody EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to unequip an item from the user.
         * @param String token
         * @Param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User user
         */
        return userService.unequipItem(token, equipUnequipItemDTO);
    }

    @PostMapping("/buyitem")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void buyItem(@RequestHeader(value = "Authorization") String token,
                        @RequestBody BuyItemDTO buyItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to buy an item from the shop.
         * @param String token
         * @param String name
         * @return none
         */
        userService.buyItem(token, buyItemDTO);
    }

    @PostMapping("/sellitem")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void sellItem(@RequestHeader(value = "Authorization") String token,
                         @RequestBody SellItemDTO sellItemDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to sell an item to the shop.
         * @param String token
         * @param SellItemDTO sellItemDTO
         * @return none
         */
        userService.sellItem(token, sellItemDTO);
    }


    //////////////////////////////////////////////////////////////////////
    ////////////////// INFO: PVP AND PVE SYSTEMS /////////////////////////
    //////////////////////////////////////////////////////////////////////

    @PostMapping("/attack-user")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody UserAttackUserDTO userAttackUserDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to attack a user.
         * @param String token
         * @param String usernameDefender-
         * @return ArrayList<ObjectNode> objectNodes
         */
        return userService.userVsUserCombatSystem(token, userAttackUserDTO);

    }

    @PostMapping("/attack-npc")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public ArrayList<ObjectNode> attackUser(@RequestHeader(value = "Authorization") String token,
                                            @RequestBody UserAttackNpcDTO userAttackNpcDTO) throws Conflict {
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
