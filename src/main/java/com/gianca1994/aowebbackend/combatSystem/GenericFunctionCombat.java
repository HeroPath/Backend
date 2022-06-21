package com.gianca1994.aowebbackend.combatSystem;

import com.gianca1994.aowebbackend.model.User;

public class GenericFunctionCombat {
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

    public int getUserDmg(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @return int
         */
        return (int) ((int) (Math.random() * (user.getMaxDmg() - user.getMinDmg())) + user.getMinDmg());
    }

    public int userReceiveDmg(User user, int dmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @param int dmg
         * @return int
         */
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
