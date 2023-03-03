package com.gianca1994.heropathbackend.combatSystem.pvp;

import com.gianca1994.heropathbackend.combatSystem.CombatModel;
import com.gianca1994.heropathbackend.combatSystem.GenericFunctions;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.guild.GuildRepository;
import com.gianca1994.heropathbackend.resources.user.User;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the combat between two users.
 */

public class PvpSystem {

    private static final GenericFunctions genericFunctions = new GenericFunctions();
    private static final PvpFunctions pvpFunctions = new PvpFunctions();

    public static CombatModel PvpUserVsUser(User user, User attacked, GuildRepository guildRepository) {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of the combat between two users.
         * @param User user
         * @param User attacked
         * @param GuildRepository guildRepository
         * @return PvpModel
         */
        CombatModel pvpModel = new CombatModel(new ArrayList<>(), user, attacked);
        int mmrWinAndLose = pvpFunctions.calculatePointsTitleWinOrLose(attacked);

        long goldAmountWin = 0, goldLoseForLoseCombat = 0;
        int roundCounter = 0, userDmg, attackedDmg, userHp = user.getHp(), userMaxDmg = user.getMaxDmg(), attackedHp = attacked.getHp(), attackedMaxDmg = attacked.getMaxDmg(), userDef = user.getDefense(), attackedDef = attacked.getDefense();
        boolean stopPvp = false;

        while (!stopPvp) {
            roundCounter++;
            if (user.getRole().equals(SvConfig.ADMIN_ROLE)) userDmg = 999999;
            else userDmg = genericFunctions.getUserDmg(user, attackedDef);

            attackedDmg = genericFunctions.getUserDmg(attacked, userDef);
            attackedHp -= userDmg;

            if (genericFunctions.checkIfUserDied(attackedHp)) {
                attackedHp = attackedDmg = 0;
                goldAmountWin = pvpFunctions.getUserGoldThief(attacked.getGold());
                pvpFunctions.updateStatsUserWin(user, attacked, goldAmountWin, mmrWinAndLose);
                pvpFunctions.updateGuilds(user, attacked, guildRepository, mmrWinAndLose);
                pvpFunctions.updateQuests(user);
                stopPvp = true;
            } else {
                userHp = genericFunctions.reduceUserHp(user, userHp, attackedDmg);
                if (genericFunctions.checkIfUserDied(userHp)) {
                    userHp = userDmg = 0;
                    goldLoseForLoseCombat = pvpFunctions.getUserGoldLoseForLoseCombat(user);
                    pvpFunctions.updateStatsUserLose(user, attacked, goldLoseForLoseCombat, mmrWinAndLose);
                    stopPvp = true;
                }
            }
            pvpModel.roundCombat(roundCounter, userHp, userDmg, userMaxDmg, attackedHp, attackedDmg, attackedMaxDmg);
        }
        pvpModel.finishCombat(userHp, 0, 0, 0, mmrWinAndLose, goldAmountWin, goldLoseForLoseCombat, false);
        user.setHp(userHp);
        attacked.setHp(attackedHp);
        return pvpModel;
    }
}
