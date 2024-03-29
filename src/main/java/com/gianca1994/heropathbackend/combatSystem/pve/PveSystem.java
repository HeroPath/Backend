package com.gianca1994.heropathbackend.combatSystem.pve;

import com.gianca1994.heropathbackend.combatSystem.CombatModel;
import com.gianca1994.heropathbackend.combatSystem.GenericFunctions;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.npc.Npc;
import com.gianca1994.heropathbackend.resources.user.User;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the combat between the user and the npc.
 */

public class PveSystem {

    private static final GenericFunctions genericFunctions = new GenericFunctions();
    private static final PveFunctions pveFunctions = new PveFunctions();

    public static CombatModel PveUserVsNpc(User user, Npc npc, float bonusExpGold) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of the combat between the user and the npc.
         * @param User user
         * @param Npc npc
         * @param TitleRepository titleRepository
         * @return PveModel
         */
        CombatModel pveModel = new CombatModel(new ArrayList<>(), user, npc);
        int roundCount = 0, diamondWin = 0, userDmg, npcDmg, userHp = user.getHp(), npcHp = npc.getMaxHp(), userMaxDmg = user.getMaxDmg(), userDef = user.getDefense(), npcDef = npc.getDefense(), npcMaxDmg = npc.getMaxDmg();
        boolean lvlUp = false, stopPve = false, diamondLuck = pveFunctions.chanceDropDiamonds();
        long expGain = 0, goldGain = 0;

        while (!stopPve) {
            roundCount++;
            userDmg = user.getRole().equals(SvConfig.ADMIN_ROLE) ? 999999 : genericFunctions.getUserDmg(user, npcDef);
            npcDmg = pveFunctions.calculateNpcDmg(npc, userDef);
            npcHp -= userDmg;

            if (pveFunctions.checkIfNpcDied(npcHp)) {
                npcHp = npcDmg = 0;
                expGain = (long) (pveFunctions.CalculateUserExperienceGain(npc) * bonusExpGold);
                goldGain = (long) (pveFunctions.calculateUserGoldGain(npc) * bonusExpGold);
                if (diamondLuck) diamondWin = pveFunctions.amountDiamondsDrop(user);

                pveFunctions.updateExpAndGold(user, expGain, goldGain);
                pveFunctions.updateQuestProgress(user, npc);
                lvlUp = user.userLevelUp();
                userHp = lvlUp ? user.getMaxHp() : userHp;
                expGain = user.getLevel() >= SvConfig.LEVEL_MAX ? 0 : expGain;
                stopPve = true;
            } else {
                userHp = genericFunctions.reduceUserHp(user, userHp, npcDmg);
                if (genericFunctions.checkIfUserDied(userHp)) {
                    userDmg = userHp = 0;
                    stopPve = true;
                }
            }
            pveModel.roundCombat(roundCount, userHp, userDmg, userMaxDmg, npcHp, npcDmg, npcMaxDmg);
        }
        pveModel.finishCombat(userHp, expGain, goldGain, diamondWin, 0, 0, 0, lvlUp);
        user.updateTitle();
        user.setHp(userHp);
        npc.setHp(npc.getMaxHp());
        return pveModel;
    }
}
