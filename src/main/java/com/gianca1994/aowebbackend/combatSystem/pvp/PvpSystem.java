package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the functions that are used in the PvpCombatSystem.
 */

public class PvpSystem {

    private static final GenericFunctions genericFunctions = new GenericFunctions();
    private static final PvpFunctions pvpFunctions = new PvpFunctions();

    public static PvpModel PvpUserVsUser(User user,
                                         User attacked,
                                         TitleRepository titleRepository,
                                         GuildRepository guildRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of starting a combat between two users.
         * @param User user
         * @param User attacked
         * @param TitleRepository titleRepository
         * @param GuildRepository guildRepository
         * @return PvpModel
         */
        PvpModel pvpModel = new PvpModel(new ArrayList<>(), user, attacked);
        int mmrWinAndLose = pvpFunctions.calculatePointsTitleWinOrLose(attacked);

        long goldAmountWin = 0, goldLoseForLoseCombat = 0;
        int roundCounter = 0, userDmg, attackedDmg, userHp = user.getHp(), attackedHp = attacked.getHp(),
                userDef = user.getDefense(), attackedDef = attacked.getDefense();
        boolean stopPvp = false;

        while (!stopPvp) {
            roundCounter++;
            if (user.getRole().getRoleName().equals("ADMIN")) userDmg = 999999999;
            else userDmg = genericFunctions.getUserDmg(user, attackedDef);

            attackedDmg = genericFunctions.getUserDmg(attacked, userDef);
            attackedHp -= userDmg;

            if (genericFunctions.checkIfUserDied(attackedHp)) {
                attackedDmg = 0;
                attackedHp = 0;
                goldAmountWin = pvpFunctions.getUserGoldThief(attacked.getGold());

                pvpFunctions.updateStatsUserWin(user, attacked, goldAmountWin, mmrWinAndLose, titleRepository);
                pvpFunctions.updateGuilds(user, attacked, guildRepository, mmrWinAndLose);
                pvpFunctions.updateQuests(user);
                stopPvp = true;
            } else {
                userHp = genericFunctions.userReceiveDmg(user, userHp, attackedDmg);
                if (genericFunctions.checkIfUserDied(userHp)) {
                    userDmg = 0;
                    userHp = 0;
                    goldLoseForLoseCombat = pvpFunctions.getUserGoldLoseForLoseCombat(user);
                    pvpFunctions.updateStatsUserLose(user, attacked, goldLoseForLoseCombat, mmrWinAndLose, titleRepository);
                    stopPvp = true;
                }
            }
            pvpModel.roundJsonGenerator(roundCounter, userHp, userDmg, attackedHp, attackedDmg);
        }
        pvpModel.roundJsonGeneratorFinish(goldAmountWin, goldLoseForLoseCombat, mmrWinAndLose);
        user.setHp(userHp);
        attacked.setHp(attackedHp);
        return pvpModel;
    }
}
