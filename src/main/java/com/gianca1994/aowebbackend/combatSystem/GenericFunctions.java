package com.gianca1994.aowebbackend.combatSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
         * @param int defense
         * @return int
         */
        int maxDifference = user.getMaxDmg() - user.getMinDmg() + 1;
        int randomNum = (int) Math.floor(Math.random() * maxDifference) + user.getMinDmg();
        int dmg = defense >= randomNum ? 0 : randomNum - defense;
        return calculateCriticalDmg(user, dmg);
    }

    private int calculateCriticalDmg(User user, int dmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the critical damage.
         * @param User user
         * @param int dmg
         * @return int
         */
        double randomNum = Math.random() * 100 + 1;
        if (randomNum <= user.getCriticalChance())
            return dmg * SvConfig.CRITICAL_DAMAGE_MULTIPLIER;
        else return dmg;
    }

    public int reduceUserHp(User user, int userHp, int dmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @param int dmg
         * @return int
         */
        if (user.getRole().equals("ADMIN")) dmg = 0;
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

    public ObjectNode roundJsonGenerator(int roundCounter, String attacker, int attackerHp, int attackerDmg,
                                         String defender, int defenderHp, int defenderDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round node.
         * @param int roundCounter
         * @param int attackerDmg
         * @param int defenderDmg
         * @param int defenderHp
         * @param int attackerHp
         * @return none
         */
        ObjectNode round = new ObjectMapper().createObjectNode();
        round.put("round", roundCounter);
        round.put(attacker + "Life", attackerHp);
        round.put(attacker + "Dmg", attackerDmg);
        round.put(defender + "Life", defenderHp);
        round.put(defender + "Dmg", defenderDmg);
        return round;
    }

    public ObjectNode roundJsonGeneratorFinish(int userHp, String attackerName, String defenderName,
                                               int mmrWinAndLose, long goldAmountWin,
                                               long goldLoseCombat, long experienceGain,
                                               long goldGain, int diamondsGain, boolean levelUp) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round finish node.
         * @param long goldAmountWin
         * @param long goldAmountLoseCombat
         * @param int amountPointsTitleWinOrLose
         * @return none
         */
        ObjectNode finalRound = new ObjectMapper().createObjectNode();

        if (userHp > 0) {
            finalRound.put("Win", attackerName);
            if (mmrWinAndLose > 0) finalRound.put("TitlePtsWin", mmrWinAndLose);
            finalRound.put("Lose", defenderName);
        } else {
            finalRound.put("Win", defenderName);
            finalRound.put("Lose", attackerName);
        }
        // If PVP Combat
        if (mmrWinAndLose > 0) finalRound.put("TitlePtsLose", mmrWinAndLose);
        if (goldAmountWin > 0) finalRound.put("GoldWin", goldAmountWin);
        if (goldLoseCombat > 0) finalRound.put("GoldLose", goldLoseCombat);

        // If PVE Combat
        if (experienceGain > 0) finalRound.put("ExperienceWin", experienceGain);
        if (goldGain > 0) finalRound.put("GoldWin", goldGain);
        if (diamondsGain > 0) finalRound.put("DiamondsWin", diamondsGain);
        if (levelUp) finalRound.put("LevelUP", true);

        return finalRound;
    }

}
