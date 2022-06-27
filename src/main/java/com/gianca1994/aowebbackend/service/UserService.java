package com.gianca1994.aowebbackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.*;
import com.gianca1994.aowebbackend.combatSystem.pve.PveFunctions;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.combatSystem.pve.PveSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpSystem;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpFunctions;
import com.gianca1994.aowebbackend.combatSystem.pvp.PvpModel;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.dto.UserAttackNpcDTO;
import com.gianca1994.aowebbackend.exception.BadRequestException;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.exception.NotFoundException;
import com.gianca1994.aowebbackend.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.NpcRepository;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import com.gianca1994.aowebbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    NpcRepository npcRepository;

    GenericFunctions genericFunctions = new GenericFunctions();

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

    public User getProfile(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return User
         */
        return userRepository.findByUsername(getTokenUser(token));
    }

    public ArrayList<User> getRankingAll() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the ranking of all users.
         * @param none
         * @return ArrayList<User>
         */
        ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
        users.sort(Comparator.comparing(User::getLevel).reversed());
        return users;
    }

    public User setFreeSkillPoint(String token, FreeSkillPointDTO freeSkillPointDTO) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding skill points to the user.
         * @param String username
         * @param FreeSkillPointDTO freeSkillPointDTO
         * @return User
         */
        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFoundException("User not found");
        if (freeSkillPointDTO.getAmount() <= 0) throw new BadRequestException("Amount must be greater than 0");
        if (user.getFreeSkillPoints() <= 0) throw new ConflictException("You don't have any free skill points");
        if (user.getFreeSkillPoints() < freeSkillPointDTO.getAmount())
            throw new ConflictException("You don't have enough free skill points");

        switch (freeSkillPointDTO.getSkillPointName()) {
            case "strength":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                user.setStrength(user.getStrength() + freeSkillPointDTO.getAmount());
                if (Objects.equals(user.getAClass().getName(), "warrior")) {
                    user.setMinDmg(user.getStrength() * 3);
                    user.setMaxDmg(user.getStrength() * 5);
                }
                break;

            case "dexterity":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                user.setDexterity(user.getDexterity() + freeSkillPointDTO.getAmount());
                if (Objects.equals(user.getAClass().getName(), "archer")) {
                    user.setMinDmg(user.getDexterity() * 4);
                    user.setMaxDmg(user.getDexterity() * 6);
                }
                break;

            case "intelligence":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                user.setIntelligence(user.getIntelligence() + freeSkillPointDTO.getAmount());
                if (Objects.equals(user.getAClass().getName(), "mage")) {
                    user.setMinDmg(user.getIntelligence() * 4);
                    user.setMaxDmg(user.getIntelligence() * 7);
                }
                break;

            case "vitality":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                user.setVitality(user.getVitality() + freeSkillPointDTO.getAmount());

                if (Objects.equals(user.getAClass().getName(), "mage")) {
                    user.setHp(user.getHp() + freeSkillPointDTO.getAmount() * 10);
                    user.setMaxHp(user.getVitality() * 10);

                } else if (Objects.equals(user.getAClass().getName(), "warrior")) {
                    user.setHp(user.getHp() + freeSkillPointDTO.getAmount() * 20);
                    user.setMaxHp(user.getVitality() * 20);

                } else if (Objects.equals(user.getAClass().getName(), "archer")) {
                    user.setHp(user.getHp() + freeSkillPointDTO.getAmount() * 15);
                    user.setMaxHp(user.getVitality() * 15);
                }
                break;

            case "luck":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                user.setLuck(user.getLuck() + freeSkillPointDTO.getAmount());
                break;

            default:
                break;
        }

        userRepository.save(user);
        return user;
    }

    //////////////////////////////////////////////////////////////////////
    ////////////////// INFO: PVP AND PVE SYSTEMS /////////////////////////
    //////////////////////////////////////////////////////////////////////

    public ArrayList<ObjectNode> userVsUserCombatSystem(String token, String usernameDefender) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between two users.
         * @param String usernameAttacker
         * @param String usernameDefender
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(getTokenUser(token));
        if (attacker == null) throw new NotFoundException("User not found");
        if (genericFunctions.checkLifeStartCombat(attacker))
            throw new BadRequestException("Impossible to attack with less than 25% of life");

        User defender = userRepository.findByUsername(usernameDefender);
        if (defender == null) throw new NotFoundException("Enemy not found");
        if (defender.getRole().getRoleName().equals("ADMIN")) throw new ConflictException("You can't attack an admin");
        if (genericFunctions.checkLifeStartCombat(defender))
            throw new BadRequestException("Impossible to attack an enemy with less than 25% of its health");

        if (attacker == defender) throw new ConflictException("You can't fight yourself");

        PvpModel pvpUserVsUserModel = PvpSystem.PvpUserVsUser(attacker, defender);

        userRepository.save(pvpUserVsUserModel.getAttacker());
        userRepository.save(pvpUserVsUserModel.getDefender());
        return pvpUserVsUserModel.getHistoryCombat();
    }


    public ArrayList<ObjectNode> userVsNpcCombatSystem(String token, UserAttackNpcDTO userAttackNpcDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat between the user and the npc.
         * @param String usernameAttacker
         * @param long npcId
         * @return ArrayList<ObjectNode>
         */
        User user = userRepository.findByUsername(getTokenUser(token));

        if (user == null) throw new NotFoundException("User not found");
        if (genericFunctions.checkLifeStartCombat(user))
            throw new BadRequestException("Impossible to attack with less than 25% of life");

        Npc npc = npcRepository.findByName(userAttackNpcDTO.getName().toLowerCase());
        if (npc == null) throw new NotFoundException("Npc not found");

        PveModel pveSystem = PveSystem.PveUserVsNpc(user, npc);

        userRepository.save(pveSystem.getUser());
        return pveSystem.getHistoryCombat();
    }
}
