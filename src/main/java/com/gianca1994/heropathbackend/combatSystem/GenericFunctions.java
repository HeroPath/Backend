package com.gianca1994.heropathbackend.combatSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.user.User;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the generic functions that are used in the combat system.
 */

public class GenericFunctions {

    public boolean checkLifeStartCombat(User user) {
        return user.getHp() < user.getMaxHp() * SvConfig.MIN_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED;
    }

    public int getUserDmg(User user, int defense) {
        int maxDifference = user.getMaxDmg() - user.getMinDmg() + 1;
        int randomNum = (int) Math.floor(Math.random() * maxDifference) + user.getMinDmg();
        int dmg = defense >= randomNum ? 0 : randomNum - defense;
        return calculateCriticalDmg(user, dmg);
    }

    private int calculateCriticalDmg(User user, int dmg) {
        double randomNum = Math.random() * 100 + 1;
        if (randomNum <= user.getCriticalChance())
            return dmg * SvConfig.CRITICAL_DAMAGE_MULTIPLIER;
        else return dmg;
    }

    public int reduceUserHp(User user, int userHp, int dmg) {
        if (user.getRole().equals(SvConfig.ADMIN_ROLE)) dmg = 0;
        return userHp - dmg;
    }

    public boolean checkIfUserDied(int userHp) {
        return userHp <= 0;
    }

    public ObjectNode roundJsonGenerator(int roundCounter, String attacker, int attackerHp, int attackerDmg,
                                         int attackerMaxDmg, String defender, int defenderHp, int defenderDmg,
                                         int defenderMaxDmg) {

        ObjectNode round = new ObjectMapper().createObjectNode();
        round.put("round", roundCounter);
        round.put(attacker + "Life", attackerHp);
        round.put(attacker + "Dmg", attackerDmg);
        if (attackerDmg > attackerMaxDmg) round.put(attacker + "Critical", true);
        round.put(defender + "Life", defenderHp);
        round.put(defender + "Dmg", defenderDmg);
        if (defenderDmg > defenderMaxDmg) round.put(defender + "Critical", true);
        return round;
    }

    public ObjectNode roundJsonGeneratorFinish(int userHp, String attackerName, String defenderName,
                                               int mmrWinAndLose, long goldAmountWin,
                                               long goldLoseCombat, long experienceGain,
                                               long goldGain, int diamondsGain, boolean levelUp) {

        ObjectNode finalRound = new ObjectMapper().createObjectNode();

        if (userHp > 0) {
            finalRound.put("Win", attackerName);
            if (mmrWinAndLose > 0) finalRound.put("TitlePtsWin", mmrWinAndLose);
            finalRound.put("Lose", defenderName);
        } else {
            finalRound.put("Win", defenderName);
            finalRound.put("Lose", attackerName);
        }
        if (mmrWinAndLose > 0) finalRound.put("TitlePtsLose", mmrWinAndLose);
        if (goldAmountWin > 0) finalRound.put("GoldWin", goldAmountWin);
        if (goldLoseCombat > 0) finalRound.put("GoldLose", goldLoseCombat);
        if (experienceGain > 0) finalRound.put("ExperienceWin", experienceGain);
        if (goldGain > 0) finalRound.put("GoldWin", goldGain);
        if (diamondsGain > 0) finalRound.put("DiamondsWin", diamondsGain);
        if (levelUp) finalRound.put("LevelUP", true);
        return finalRound;
    }
}
