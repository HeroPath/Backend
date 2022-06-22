package com.gianca1994.aowebbackend.combatSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.User;

public class PvpUserVsUser {
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

    public long getUserGoldAmountWin(User attacker, User defender) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold that the attacker.
         * @param User attacker
         * @param User defender
         * @return long
         */
        return attacker.getGold() + getUserGoldThief(defender);
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

    public ObjectNode roundJsonGeneratorUserVsUser(User attacker, User defender,
                                                  int roundCounter, int attackerDmg, int defenderDmg) {
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
}
