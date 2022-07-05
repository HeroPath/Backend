package com.gianca1994.aowebbackend.combatSystem;

import com.gianca1994.aowebbackend.model.User;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the generic functions that are used in the combat system.
 */
public class GenericFunctions {
    private final float MINIMUM_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED = 0.25f;

    public boolean checkLifeStartCombat(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has enough
         * life to start a combat.
         * @param User user
         * @return boolean
         */
        return user.getHp() < user.getMaxHp() * MINIMUM_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED;
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
        if (Math.floor(Math.random() * 100) + 1 <= user.getCriticalChance()) return dmg * 2;
        else return dmg;
    }

    public int userReceiveDmg(User user, int dmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @param int dmg
         * @return int
         */
        if (user.getRole().getRoleName().equals("ADMIN")) dmg = 0;
        return user.getHp() - dmg;
    }

    public boolean checkIfUserDied(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has died.
         * @param User user
         * @return boolean
         */
        return user.getHp() <= 0;
    }
}
