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
         * Explanation: This function is in charge of the combat between the user and the npc.
         * @param User user
         * @param Npc npc
         * @param TitleRepository titleRepository
         * @return PveModel
         */
        GenericFunctions genericFunctions = new GenericFunctions();
        PveFunctions pveFunctions = new PveFunctions();

        PveModel pveModel = new PveModel(new ArrayList<>(), user, npc);

        long experienceGain = 0, goldGain = 0;
        int diamondsGain = 0, roundCounter = 0;
        boolean levelUp = false, userAlive = true, npcAlive = true;

        do {
            roundCounter++;
            int userDmg = genericFunctions.getUserDmg(user, npc.getDefense());
            int npcDmg = pveFunctions.calculateNpcDmg(npc, user.getDefense());

            if (userAlive && npcAlive) {
                npc.setHp(npc.getHp() - userDmg);

                if (pveFunctions.checkIfNpcDied(npc)) {
                    experienceGain = pveFunctions.CalculateUserExperienceGain(npc);
                    goldGain = pveFunctions.calculateUserGoldGain(npc);

                    pveFunctions.updateExpGldNpcsKilled(user, experienceGain, goldGain);
                    pveFunctions.updateQuestProgress(user, npc);

                    levelUp = user.userLevelUp();

                    npc.setHp(0);
                    npcDmg = 0;
                    npcAlive = false;
                } else {
                    user.setHp(genericFunctions.userReceiveDmg(user, npcDmg));

                    if (genericFunctions.checkIfUserDied(user)) {
                        user.setHp(0);
                        userDmg = 0;
                        userAlive = false;
                    }
                }
            }
            pveModel.roundJsonGenerator(roundCounter, userDmg, npcDmg);
        } while (pveFunctions.checkUserAndNpcAlive(userAlive, npcAlive));

        if (pveFunctions.chanceDropDiamonds()) diamondsGain = pveFunctions.amountDiamondsDrop(user);

        user.checkStatusTitlePoints(titleRepository);
        pveModel.roundJsonGeneratorFinish(
                0, 0, 0,
                experienceGain, goldGain, diamondsGain, levelUp);

        npc.setHp(npc.getMaxHp());
        return pveModel;
    }
}
