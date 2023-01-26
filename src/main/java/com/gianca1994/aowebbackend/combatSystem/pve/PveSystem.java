package com.gianca1994.aowebbackend.combatSystem.pve;

import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.UserQuest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Gianca1994
 * Explanation: PveSystem
 */
public class PveSystem {

    public static PveModel PveUserVsNpc(User user,
                                        Npc npc,
                                        TitleRepository titleRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of starting a combat between a user and a npc.
         * @param User user
         * @param Npc npc
         * @return PveModel
         */
        GenericFunctions genericFunctions = new GenericFunctions();
        PveFunctions pveFunctions = new PveFunctions();

        PveModel pveModel = new PveModel(new ArrayList<>(), user, npc);

        long experienceGain = 0, goldGain = 0, experienceQuestGain = 0, goldQuestGain = 0;
        int diamondsGain = 0, roundCounter = 0;
        boolean levelUp = false, stopPvP = false;

        do {
            roundCounter++;
            int userDmg = genericFunctions.getUserDmg(user, npc.getDefense());
            int npcDmg = pveFunctions.calculateNpcDmg(npc, user.getDefense());

            // Check if the finish combat.
            if (!stopPvP) {
                npc.setHp(npc.getHp() - userDmg);

                // Check if the npc has died.
                if (pveFunctions.checkIfNpcDied(npc)) {
                    npc.setHp(0);
                    npcDmg = 0;
                    // Add the history of the combat.
                    user.setNpcKills(user.getNpcKills() + 1);

                    Map<String, UserQuest> userQuests = new HashMap<>();
                    for (UserQuest quest : user.getUserQuests()) {
                        userQuests.put(quest.getQuest().getNameNpcKill(), quest);
                    }

                    UserQuest quest = userQuests.get(npc.getName());
                    if (quest != null && !npc.getName().equals("player") && quest.getAmountNpcKill() < quest.getQuest().getNpcKillAmountNeeded()) {
                        quest.setAmountNpcKill(quest.getAmountNpcKill() + 1);
                    }

                    experienceGain = pveFunctions.CalculateUserExperienceGain(npc);
                    user.setExperience(user.getExperience() + experienceGain + experienceQuestGain);

                    goldGain = pveFunctions.calculateUserGoldGain(npc);
                    user.setGold(user.getGold() + goldGain + goldQuestGain);

                    levelUp = user.userLevelUp();
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
            pveModel.roundJsonGenerator(roundCounter, userDmg, npcDmg);

        } while (pveFunctions.checkUserAndNpcAlive(user, npc));

        if (pveFunctions.chanceDropDiamonds()) {
            diamondsGain = pveFunctions.amountOfDiamondsDrop();
            user.setDiamond(user.getDiamond() + diamondsGain);
        }

        user.checkStatusTitlePoints(titleRepository);

        pveModel.roundJsonGeneratorFinish(
                0,0,0,
                experienceGain, goldGain, diamondsGain, levelUp);

        npc.setHp(npc.getMaxHp());
        return pveModel;
    }

}
