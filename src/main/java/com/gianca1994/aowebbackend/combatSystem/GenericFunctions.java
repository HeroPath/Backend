package com.gianca1994.aowebbackend.combatSystem;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.user.User;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the generic functions that are used in the combat system.
 */
public class GenericFunctions {

    public boolean checkLifeStartCombat(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has enough
         * life to start a combat.
         * @param User user
         * @return boolean
         */
        return user.getHp() < user.getMaxHp() * SvConfig.MIN_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED;
    }

    public int getUserDmg(User user, int defense) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @return int
         */
        int dmg = (int) Math.floor(Math.random() * (user.getMaxDmg() - user.getMinDmg() + 1) + user.getMinDmg());
        dmg = defense >= dmg ? 0 : dmg - defense;
        if (Math.floor(Math.random() * 100) + 1 <= user.getCriticalChance())
            return dmg * SvConfig.CRITICAL_DAMAGE_MULTIPLIER;
        else return dmg;
    }

    public int userReceiveDmg(User user, int userHp, int dmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @param int dmg
         * @return int
         */
        if (user.getRole().getRoleName().equals("ADMIN")) dmg = 0;
        return userHp - dmg;
    }

    public boolean checkIfUserDied(int userHp) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has died.
         * @param User user
         * @return boolean
         */
        return userHp <= 0;
    }
}
