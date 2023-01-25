package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.user.User;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the functions that are used in the PvpCombatSystem.
 */
public class PvpFunctions {
    private long getUserGoldThief(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold theft.
         * @param User user
         * @return long
         */
        return (long) (user.getGold() * SvConfig.PVP_GOLD_WIN_RATE);
    }

    public long getUserGoldAmountWin(User defender) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold that the attacker.
         * @param User attacker
         * @param User defender
         * @return long
         */
        return getUserGoldThief(defender);
    }

    public long getUserGoldAmountLose(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold that the attacker.
         * @param User user
         * @return long
         */
        return user.getGold() - getUserGoldThief(user);
    }

    public long getUserGoldLoseForLoseCombat(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold that the attacker.
         * @param User user
         * @return long
         */
        return (long) (user.getGold() * SvConfig.PVP_GOLD_LOSS_RATE);
    }

    public boolean checkBothUsersAlive(User attacker, User defender) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if both fighters are alive.
         * @param User user
         * @param User defender
         * @return boolean
         */
        return attacker.getHp() > 0 && defender.getHp() > 0;
    }

    public int calculatePointsTitleWinOrLose() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the points that the attacker.
         * @param none
         * @return int
         */
        return (int) Math.floor(Math.random() * (SvConfig.PVP_MAX_RATE_POINT_TITLE - SvConfig.PVP_MIN_RATE_POINT_TITLE + 1) + SvConfig.PVP_MIN_RATE_POINT_TITLE);
    }
}
