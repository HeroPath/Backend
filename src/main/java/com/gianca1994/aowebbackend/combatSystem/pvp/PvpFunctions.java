package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.User;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the functions that are used in the PvpCombatSystem.
 */
public class PvpFunctions {
    private final float PVP_GOLD_THEFT_RATE = 0.25f;
    private final float PVP_GOLD_LOSS_RATE = 0.1f;


    private long getUserGoldThief(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold theft.
         * @param User user
         * @return long
         */
        return (long) (user.getGold() * PVP_GOLD_THEFT_RATE);
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
        return (long) (user.getGold() - (user.getGold() * PVP_GOLD_LOSS_RATE));
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

    public ObjectNode roundJsonGeneratorUserVsUser(
            User attacker,
            User defender,
            int roundCounter,
            int attackerDmg,
            int defenderDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of generating the json for the round.
         * @param User attacker
         * @param User defender
         * @param int roundCounter
         * @param int attackerDmg
         * @param int defenderDmg
         * @return ObjectNode
         */
        ObjectNode round = new ObjectMapper().createObjectNode();
        round.put("round", roundCounter);
        round.put("attackerLife", attacker.getHp());
        round.put("defenderLife", defender.getHp());
        round.put("attackerDmg", attackerDmg);
        round.put("defenderDmg", defenderDmg);
        return round;
    }

    public ObjectNode roundJsonGeneratorUserVsUserFinish(
            User attacker,
            User defender,
            long goldAmountWin,
            long goldAmountLoseForLoseCombat) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of generating the json for the round.
         * @param User attacker
         * @param User defender
         * @param long goldAmountWin
         * @param long goldAmountLoseForLoseCombat
         * @return ObjectNode
         */
        ObjectNode round = new ObjectMapper().createObjectNode();

        if (attacker.getHp() > 0) {
            round.put("win", attacker.getUsername());
            round.put("lose", defender.getUsername());
        } else {
            round.put("win", defender.getUsername());
            round.put("lose", attacker.getUsername());
        }

        if (goldAmountWin > 0) round.put("goldAmountWin", goldAmountWin);
        if (goldAmountLoseForLoseCombat > 0) round.put("goldAmountLoseForLoseCombat", goldAmountLoseForLoseCombat);

        return round;
    }
}
