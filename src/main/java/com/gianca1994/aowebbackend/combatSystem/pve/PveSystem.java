package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: PveSystem
 */
public class PveSystem {

    public static PveModel PveUserVsNpc(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of starting a combat between a user and an npc.
         * @param User user
         * @param Npc npc
         * @return PveModel
         */
        final short LEVEL_MAX = 300;

        GenericFunctions genericFunctions = new GenericFunctions();
        PveFunctions pveFunctions = new PveFunctions();
        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        long experienceGain = 0;
        long goldGain = 0;
        int diamondsGain = 0;
        boolean levelUp = false;


        int roundCounter = 0;
        boolean stopPvP = false;

        do {
            roundCounter++;
            int userDmg = genericFunctions.getUserDmg(user);
            int npcDmg = pveFunctions.calculateNpcDmg(npc);

            // Check if the finish combat.
            if (!stopPvP) {
                npc.setHp(npc.getHp() - userDmg);

                // Check if the npc has died.
                if (pveFunctions.checkIfNpcDied(npc)) {
                    npc.setHp(0);
                    npcDmg = 0;
                    // Add the history of the combat.
                    user.setNpcKills(user.getNpcKills() + 1);

                    if (user.getLevel() < LEVEL_MAX) {
                        experienceGain = pveFunctions.CalculateUserExperienceGain(npc);
                        user.setExperience(user.getExperience() + experienceGain);
                    }

                    goldGain = pveFunctions.calculateUserGoldGain(npc);
                    user.setGold(user.getGold() + goldGain);

                    // Check if the user has enough experience to level up.
                    if (pveFunctions.checkUserLevelUp(user)) {
                        do {
                            user.setHp(user.getMaxHp());
                            user.setLevel(pveFunctions.userLevelUp(user));
                            user.setFreeSkillPoints(pveFunctions.freeSkillPointsAdd(user));
                            levelUp = true;

                            if (user.getLevel() < LEVEL_MAX) {
                                user.setExperience(user.getExperience() - user.getExperienceToNextLevel());
                                user.setExperienceToNextLevel(pveFunctions.userLevelUpNewNextExpToLevel(user));
                            } else {
                                user.setExperience(0);
                                user.setExperienceToNextLevel(0);
                            }

                        } while (pveFunctions.checkUserLevelUp(user));
                    }
                    stopPvP = true;
                } else {
                    // Check if the user has died.
                    user.setHp(genericFunctions.userReceiveDmg(user, npcDmg));
                    if (genericFunctions.checkIfUserDied(user)) {
                        user.setHp(0);
                        userDmg = 0;
                        stopPvP = true;
                    }
                }
            }

            historyCombat.add(pveFunctions.roundJsonGeneratorUserVsNpc(
                    user, npc, roundCounter, userDmg, npcDmg)
            );

        } while (pveFunctions.checkUserAndNpcAlive(user, npc));

        if (pveFunctions.chanceDropDiamonds()) {
            diamondsGain = pveFunctions.amountOfDiamondsDrop();
            user.setDiamond(user.getDiamond() + diamondsGain);
        }

        historyCombat.add(pveFunctions.roundJsonGeneratorUserVsNpcFinish(
                user, npc, experienceGain, goldGain, diamondsGain, levelUp));

        npc.setHp(npc.getMaxHp());
        return new PveModel(user, historyCombat);
    }

}
