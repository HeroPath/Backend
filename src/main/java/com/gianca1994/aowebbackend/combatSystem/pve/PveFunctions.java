package com.gianca1994.aowebbackend.combatSystem.pve;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;

/**
 * @Author: Gianca1994
 * Explanation: PveFunctions
 */
public class PveFunctions {
    public int calculateNpcDmg(Npc npc, int userDefense) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the npc.
         * @param Npc npc
         * @return int
         */
        int npcDmg = (int) Math.floor(Math.random() * (npc.getMaxDmg() - npc.getMinDmg() + 1) + npc.getMinDmg());
        return userDefense >= npcDmg ? 0 : npcDmg - userDefense;
    }

    public long CalculateUserExperienceGain(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the experience gain.
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxExp() - npc.getGiveMinExp() + 1) + npc.getGiveMinExp())) * SvConfig.EXPERIENCE_MULTIPLIER;
    }

    public boolean checkIfNpcDied(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the npc has died.
         * @param Npc npc
         * @return boolean
         */
        return npc.getHp() <= 0;
    }

    public long calculateUserGoldGain(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold gain.
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxGold() - npc.getGiveMinGold() + 1) + npc.getGiveMinGold())) * SvConfig.GOLD_MULTIPLIER;
    }

    public boolean checkUserAndNpcAlive(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user and the npc are alive.
         * @param User user
         * @param Npc npc
         * @return boolean
         */
        return user.getHp() > 0 && npc.getHp() > 0;
    }

    public boolean chanceDropDiamonds() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the chance of dropping diamonds.
         * @return boolean
         */
        return ((Math.random() * 100) + 1) > (100 - SvConfig.DIAMOND_DROP_CHANCE_PERCENTAGE);
    }

    public int amountOfDiamondsDrop() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the amount of diamonds that will be dropped.
         * @return int
         */
        return (int) (Math.random() * SvConfig.MAXIMUM_AMOUNT_DIAMONDS_DROP) + 1;
    }
}
