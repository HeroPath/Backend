package com.gianca1994.aowebbackend.service;

import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctionCombat;
import com.gianca1994.aowebbackend.combatSystem.PvpUserVsUser;
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

    public User getProfile(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the profile of the user.
         * @param String username
         * @return User
         */
        return userRepository.findByUsername(username);
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

    //////////////////////////////////////////////////////////////////////
    ////////////// INFO: Abajo, lo relacionado al PVP y PVE //////////////
    //////////////////////////////////////////////////////////////////////

    public ArrayList<ObjectNode> userVsUserCombatSystem(String usernameAttacker, String usernameDefender) {
        User attacker = userRepository.findByUsername(usernameAttacker);
        User defender = userRepository.findByUsername(usernameDefender);

        if (attacker == null || defender == null) return null;
        if (genericFunctionCombat.checkLifeStartCombat(attacker) ||
                genericFunctionCombat.checkLifeStartCombat(defender)) return null;

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
                    attacker.setGold(pvpUserVsUser.getUserGoldAmountWin(attacker, defender));
                    defender.setGold(pvpUserVsUser.getUserGoldAmountLose(defender));
                    stopPvP = true;

                } else {
                    attacker.setHp(genericFunctionCombat.userReceiveDmg(attacker, defenderDmg));

                    // Check if the attacker has died.
                    if (genericFunctionCombat.checkIfUserDied(attacker)) {
                        attacker.setHp(0);
                        attacker.setGold(pvpUserVsUser.getUserGoldLoseForLoseCombat(attacker));
                        stopPvP = true;
                    }
                }
            }
            historyCombat.add(pvpUserVsUser.roundJsonGeneratorUserVsUser(
                    attacker, defender, roundCounter, attackerDmg, defenderDmg));


        } while (attacker.getHp() > 0 && defender.getHp() > 0);

        userRepository.save(attacker);
        userRepository.save(defender);

        return historyCombat;
    }


    public ArrayList<ObjectNode> userVsNpcCombatSystem(String username, long npcId) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat between the user and the npc.
         * @param String usernameAttacker
         * @param long npcId
         * @return ArrayList<ObjectNode>
         */

        User user = userRepository.findByUsername(username);

        if (user == null) return null;
        if (genericFunctionCombat.checkLifeStartCombat(user)) return null;

        Npc npc = npcRepository.findById(npcId).get();
        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        int roundCounter = 0;
        boolean stopPvP = false;

        do {
            roundCounter++;
            int userDmg = genericFunctionCombat.getUserDmg(user);
            int npcDmg = pveUserVsNpc.calculateNpcDmg(npc);

            // El pvp concluyo?
            if (!stopPvP) {
                npc.setHp(npc.getHp() - userDmg);

                // El npc murio?
                if (pveUserVsNpc.checkIfNpcDied(npc)) {
                    npc.setHp(0);
                    user.setExperience(pveUserVsNpc.CalculateUserExperienceGain(user, npc));
                    user.setGold(pveUserVsNpc.calculateUserGoldGain(user, npc));

                    // Usuario sube de nivel?
                    if (pveUserVsNpc.checkUserLevelUp(user)) {
                        user.setLevel(pveUserVsNpc.userLevelUp(user));
                        user.setExperienceToNextLevel(pveUserVsNpc.userLevelUpNewNextExpToLevel(user));
                    }
                    stopPvP = true;
                } else {
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

        } while (user.getHp() > 0 && npc.getHp() > 0);

        npc.setHp(npc.getMaxHp());
        userRepository.save(user);

        return historyCombat;
    }
}
