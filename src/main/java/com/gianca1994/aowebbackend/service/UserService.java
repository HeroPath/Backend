package com.gianca1994.aowebbackend.service;

import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctionCombat;
import com.gianca1994.aowebbackend.combatSystem.PvpUserVsUser;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.dto.UserAttackNpcDTO;
import com.gianca1994.aowebbackend.exception.BadRequestException;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.exception.NotFoundException;
import com.gianca1994.aowebbackend.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.combatSystem.PveUserVsNpc;
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

    GenericFunctionCombat genericFunctionCombat = new GenericFunctionCombat();
    PvpUserVsUser pvpUserVsUser = new PvpUserVsUser();
    PveUserVsNpc pveUserVsNpc = new PveUserVsNpc();

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
        if (user.getFreeSkillPoints() <= 0) throw new ConflictException("You don't have any free skill points");

        switch (freeSkillPointDTO.getSkillPointName()) {
            case "strength":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - 1);
                user.setStrength(user.getStrength() + 1);
                break;
            case "dexterity":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - 1);
                user.setDexterity(user.getDexterity() + 1);
                break;
            case "intelligence":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - 1);
                user.setIntelligence(user.getIntelligence() + 1);
                break;
            case "vitality":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - 1);
                user.setVitality(user.getVitality() + 1);
                break;
            case "luck":
                user.setFreeSkillPoints(user.getFreeSkillPoints() - 1);
                user.setLuck(user.getLuck() + 1);
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

    public ArrayList<ObjectNode> userVsUserCombatSystem(String token, String usernameDefender) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat system between two users.
         * @param String usernameAttacker
         * @param String usernameDefender
         * @return ArrayList<ObjectNode>
         */
        User attacker = userRepository.findByUsername(getTokenUser(token));
        User defender = userRepository.findByUsername(usernameDefender);

        if (attacker == null) throw new NotFoundException("User not found");
        if (defender == null) throw new NotFoundException("Enemy not found");
        if (genericFunctionCombat.checkLifeStartCombat(attacker))
            throw new BadRequestException("Impossible to attack with less than 25% of life");
        if (genericFunctionCombat.checkLifeStartCombat(defender))
            throw new BadRequestException("Impossible to attack an enemy with less than 25% of its health");

        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        int roundCounter = 0;
        boolean stopPvP = false;
        do {
            roundCounter++;

            // Calculate the damage of the attacker and defender.
            int attackerDmg = genericFunctionCombat.getUserDmg(attacker);
            int defenderDmg = genericFunctionCombat.getUserDmg(defender);

            if (!stopPvP) {
                defender.setHp(genericFunctionCombat.userReceiveDmg(defender, attackerDmg));

                // Check if the defender has died.
                if (genericFunctionCombat.checkIfUserDied(defender)) {
                    defender.setHp(0);
                    stopPvP = true;

                    // Add the history of the combat.
                    defender.setPvpLosses(defender.getPvpLosses() + 1);
                    attacker.setPvpWins(attacker.getPvpWins() + 1);

                    attacker.setGold(pvpUserVsUser.getUserGoldAmountWin(attacker, defender));
                    defender.setGold(pvpUserVsUser.getUserGoldAmountLose(defender));

                } else {
                    attacker.setHp(genericFunctionCombat.userReceiveDmg(attacker, defenderDmg));

                    // Check if the attacker has died.
                    if (genericFunctionCombat.checkIfUserDied(attacker)) {
                        attacker.setHp(0);
                        stopPvP = true;

                        // Add the history of the combat.
                        attacker.setPvpLosses(defender.getPvpLosses() + 1);
                        defender.setPvpWins(attacker.getPvpWins() + 1);

                        attacker.setGold(pvpUserVsUser.getUserGoldLoseForLoseCombat(attacker));
                    }
                }
            }
            historyCombat.add(pvpUserVsUser.roundJsonGeneratorUserVsUser(
                    attacker, defender, roundCounter, attackerDmg, defenderDmg));


        } while (pvpUserVsUser.checkBothUsersAlive(attacker, defender));

        userRepository.save(attacker);
        userRepository.save(defender);
        return historyCombat;
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
        if (genericFunctionCombat.checkLifeStartCombat(user))
            throw new BadRequestException("Impossible to attack with less than 25% of life");

        Npc npc = npcRepository.findById(userAttackNpcDTO.getNpcId()).get();
        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        int roundCounter = 0;
        boolean stopPvP = false;

        do {
            roundCounter++;
            int userDmg = genericFunctionCombat.getUserDmg(user);
            int npcDmg = pveUserVsNpc.calculateNpcDmg(npc);

            // Check if the finish combat.
            if (!stopPvP) {
                npc.setHp(npc.getHp() - userDmg);

                // Check if the npc has died.
                if (pveUserVsNpc.checkIfNpcDied(npc)) {
                    npc.setHp(0);

                    // Add the history of the combat.
                    user.setNpcKills(user.getNpcKills() + 1);

                    user.setExperience(pveUserVsNpc.CalculateUserExperienceGain(user, npc));
                    user.setGold(pveUserVsNpc.calculateUserGoldGain(user, npc));

                    // Check if the user has enough experience to level up.
                    if (pveUserVsNpc.checkUserLevelUp(user)) {
                        do {
                            user.setLevel(pveUserVsNpc.userLevelUp(user));
                            user.setExperienceToNextLevel(pveUserVsNpc.userLevelUpNewNextExpToLevel(user));
                            user.setFreeSkillPoints(pveUserVsNpc.freeSkillPointsAdd(user));
                        } while (pveUserVsNpc.checkUserLevelUp(user));
                    }
                    stopPvP = true;
                } else {
                    // Check if the user has died.
                    user.setHp(genericFunctionCombat.userReceiveDmg(user, npcDmg));
                    if (genericFunctionCombat.checkIfUserDied(user)) {
                        user.setHp(0);
                        stopPvP = true;
                    }
                }
            }

            historyCombat.add(pveUserVsNpc.roundJsonGeneratorUserVsNpc(
                    user, npc, roundCounter, userDmg, npcDmg)
            );

        } while (pveUserVsNpc.checkUserAndNpcAlive(user, npc));

        if (pveUserVsNpc.chanceDropDiamonds())
            user.setDiamond(user.getDiamond() + pveUserVsNpc.amountOfDiamondsDrop());

        npc.setHp(npc.getMaxHp());
        userRepository.save(user);

        return historyCombat;
    }
}
