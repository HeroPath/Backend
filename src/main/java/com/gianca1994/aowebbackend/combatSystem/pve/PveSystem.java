package com.gianca1994.aowebbackend.combatSystem.pve;

import com.gianca1994.aowebbackend.combatSystem.CombatModel;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: PveSystem
 */
public class PveSystem {

    private static final GenericFunctions genericFunctions = new GenericFunctions();
    private static final PveFunctions pveFunctions = new PveFunctions();

    public static CombatModel PveUserVsNpc(User user, Npc npc, float bonusExpGold) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of the combat between the user and the npc.
         * @param User user
         * @param Npc npc
         * @param TitleRepository titleRepository
         * @return PveModel
         */
        CombatModel pveModel = new CombatModel(new ArrayList<>(), user, npc);
        int roundCounter = 0, diamondsGain = 0, userDmg, npcDmg;
        long experienceGain = 0, goldGain = 0;
        boolean levelUp = false, stopPve = false;
        boolean chanceDropDiamonds = pveFunctions.chanceDropDiamonds();
        int userHp = user.getHp(), npcHp = npc.getMaxHp(), userDefense = user.getDefense(),
                npcDefense = npc.getDefense();

        while (!stopPve) {
            roundCounter++;
            if (user.getRole().equals("ADMIN")) userDmg = 9999999;
            else userDmg = genericFunctions.getUserDmg(user, npcDefense);
            npcDmg = pveFunctions.calculateNpcDmg(npc, userDefense);
            npcHp -= userDmg;

            if (pveFunctions.checkIfNpcDied(npcHp)) {
                npcDmg = 0;
                npcHp = 0;

                experienceGain = (long) (pveFunctions.CalculateUserExperienceGain(npc) * bonusExpGold);
                goldGain = (long) (pveFunctions.calculateUserGoldGain(npc) * bonusExpGold);

                if (chanceDropDiamonds) diamondsGain = pveFunctions.amountDiamondsDrop(user);
                pveFunctions.updateExpGldNpcsKilled(user, experienceGain, goldGain);
                pveFunctions.updateQuestProgress(user, npc);
                levelUp = user.userLevelUp();
                if (levelUp) userHp = user.getMaxHp();
                stopPve = true;
            } else {
                userHp = genericFunctions.userReceiveDmg(user, userHp, npcDmg);
                if (genericFunctions.checkIfUserDied(userHp)) {
                    userHp = 0;
                    userDmg = 0;
                    stopPve = true;
                }
            }
            pveModel.roundJsonGenerator(roundCounter, userHp, userDmg, npcHp, npcDmg);
        }
        pveModel.roundJsonGeneratorFinish(userHp, experienceGain, goldGain, diamondsGain, 0, 0, 0, levelUp);
        user.updateTitle();
        user.setHp(userHp);
        npc.setHp(npc.getMaxHp());
        return pveModel;
    }
}
