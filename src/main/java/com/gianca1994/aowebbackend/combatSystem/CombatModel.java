package com.gianca1994.aowebbackend.combatSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to represent a combat.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class CombatModel {

    private ArrayList<ObjectNode> historyCombat;
    private User user;

    public abstract void roundJsonGenerator(int roundCounter, int userHp, int userDmg,
                                            int npcHp, int npcDmg);

    public void roundJsonGeneratorFinish(long goldAmountWin, long goldAmountLoseCombat,
                                         int amountPointsTitleWinOrLose, long experienceGain,
                                         long goldGain, int diamondsGain, boolean levelUp) {
    }

    protected ObjectNode createBasicRoundNode(int roundCounter, int attackerHp, int attackerDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round node.
         * @param int roundCounter
         * @param int attackerDmg
         * @return ObjectNode
         */
        ObjectNode round = new ObjectMapper().createObjectNode();
        round.put("round", roundCounter);
        round.put("attackerLife", attackerHp);
        round.put("attackerDmg", attackerDmg);
        return round;
    }

    protected ObjectNode createBasicRoundFinishNode(long experienceGain, long goldGain,
                                                    int diamondsGain, boolean levelUp,
                                                    long goldAmountWin, long goldAmountLoseCombat) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round finish node.
         * @param long experienceGain
         * @param long goldGain
         * @param int diamondsGain
         * @param boolean levelUp
         * @param long goldAmountWin
         * @param long goldAmountLoseCombat
         * @return ObjectNode
         */
        ObjectNode round = new ObjectMapper().createObjectNode();

        if (goldAmountWin > 0) round.put("goldAmountWin", goldAmountWin);
        if (goldAmountLoseCombat > 0) round.put("goldAmountLoseCombat", goldAmountLoseCombat);

        if (experienceGain > 0) round.put("userExperienceGain", experienceGain);
        if (goldGain > 0) round.put("goldAmountWin", goldGain);
        if (diamondsGain > 0) round.put("diamondsAmountWin", diamondsGain);
        if (levelUp) round.put("levelUp", true);

        return round;
    }

}
