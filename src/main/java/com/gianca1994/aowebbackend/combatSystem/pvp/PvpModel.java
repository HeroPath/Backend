package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.CombatModel;
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
public class PvpModel extends CombatModel {

    private User defender;

    public PvpModel(ArrayList<ObjectNode> historyCombat, User user, User defender) {
        super(historyCombat, user);
        this.defender = defender;
    }

    @Override
    public void roundJsonGenerator(int roundCounter, int attackerDmg, int defenderDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round node.
         * @param int roundCounter
         * @param int attackerDmg
         * @param int defenderDmg
         * @return none
         */
        ObjectNode round = createBasicRoundNode(roundCounter, attackerDmg);
        round.put("defenderLife", defender.getHp());
        round.put("defenderDmg", defenderDmg);
        this.getHistoryCombat().add(round);
    }

    @Override
    public void roundJsonGeneratorFinish(long goldAmountWin, long goldAmountLoseCombat,
                                         int amountPointsTitleWinOrLose, long experienceGain,
                                         long goldGain, int diamondsGain, boolean levelUp) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round finish node.
         * @param long goldAmountWin
         * @param long goldAmountLoseCombat
         * @param int amountPointsTitleWinOrLose
         * @param long experienceGain
         * @param long goldGain
         * @param long diamondsGain
         * @param boolean levelUp
         * @return none
         */
        ObjectNode round = createBasicRoundFinishNode(
                0, 0, 0, false,
                goldAmountWin, goldAmountLoseCombat);

        if (this.getUser().getHp() > 0) {
            round.put("win", this.getUser().getUsername());
            round.put("titlePointsWin", amountPointsTitleWinOrLose);
            round.put("lose", defender.getUsername());
            round.put("titlePointsLose", amountPointsTitleWinOrLose);
        } else {
            round.put("win", defender.getUsername());
            round.put("lose", this.getUser().getUsername());
            round.put("titlePointsLose", amountPointsTitleWinOrLose);
        }

        this.getHistoryCombat().add(round);
    }
}
