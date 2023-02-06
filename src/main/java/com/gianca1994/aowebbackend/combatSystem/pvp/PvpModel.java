package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.*;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to represent a PvP combat.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PvpModel {

    private static final GenericFunctions genericFunctions = new GenericFunctions();

    private ArrayList<ObjectNode> historyCombat;
    private User user;
    private User defender;

    public void roundJsonGenerator(int roundCounter, int attackerHp, int attackerDmg,
                                   int defenderHp, int defenderDmg) {
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
        this.getHistoryCombat().add(genericFunctions.roundJsonGenerator(roundCounter, "Attacker",
                attackerHp, attackerDmg, "Defender", defenderHp, defenderDmg)
        );
    }

    public void roundJsonGeneratorFinish(int attackerHp, String attackerName, String defenderName,
                                         int mmrWinAndLose, long goldAmountWin, long goldAmountLoseCombat) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round finish node.
         * @param long goldAmountWin
         * @param long goldAmountLoseCombat
         * @param int amountPointsTitleWinOrLose
         * @return none
         */
        this.getHistoryCombat().add(
                genericFunctions.roundJsonGeneratorFinish(attackerHp, attackerName,
                        defenderName, mmrWinAndLose, goldAmountWin,
                        goldAmountLoseCombat, 0, 0, 0, false
                )
        );
    }
}
