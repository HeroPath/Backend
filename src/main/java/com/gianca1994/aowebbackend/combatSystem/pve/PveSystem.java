package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: PveSystem
 */
public class PveSystem {

    private static final GenericFunctions genericFunctions = new GenericFunctions();
    private static final PveFunctions pveFunctions = new PveFunctions();

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
        PveModel pveModel = new PveModel(new ArrayList<>(), user, npc);

        int roundCounter = 0, diamondsGain = 0, userDmg, npcDmg;
        long experienceGain = 0, goldGain = 0;
        boolean levelUp = false, stopPve = false;

        boolean chanceDropDiamonds = pveFunctions.chanceDropDiamonds();
        int userHp = user.getHp(), npcHp = npc.getHp(), userDefense = user.getDefense(),
                npcDefense = npc.getDefense(), npcMaxHp = npc.getMaxHp();

        while (!stopPve) {
            roundCounter++;

            if (user.getRole().getRoleName().equals("ADMIN")) userDmg = 999999999;
            else userDmg = genericFunctions.getUserDmg(user, npcDefense);

            npcDmg = pveFunctions.calculateNpcDmg(npc, userDefense);
            npcHp -= userDmg;

            if (pveFunctions.checkIfNpcDied(npcHp)) {
                experienceGain = pveFunctions.CalculateUserExperienceGain(npc);
                goldGain = pveFunctions.calculateUserGoldGain(npc);
                if (chanceDropDiamonds) diamondsGain = pveFunctions.amountDiamondsDrop(user);

                pveFunctions.updateExpGldNpcsKilled(user, experienceGain, goldGain);
                pveFunctions.updateQuestProgress(user, npc);
                levelUp = user.userLevelUp();
                stopPve = true;
            } else {
                userHp = genericFunctions.userReceiveDmg(user, npcDmg);
                if (genericFunctions.checkIfUserDied(userHp)) stopPve = true;
            }
            pveModel.roundJsonGenerator(roundCounter, userHp, userDmg, npcHp, npcDmg);
        }
        pveModel.roundJsonGeneratorFinish(experienceGain, goldGain, diamondsGain, levelUp);

        user.checkStatusTitlePoints(titleRepository);
        npc.setHp(npcMaxHp);
        return pveModel;
    }
}
