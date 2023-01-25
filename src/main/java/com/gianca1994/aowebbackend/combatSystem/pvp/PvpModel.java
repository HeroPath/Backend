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
        ObjectNode round = createBasicRoundNode(roundCounter, attackerDmg);
        round.put("defenderLife", defender.getHp());
        round.put("defenderDmg", defenderDmg);
        this.getHistoryCombat().add(round);
    }

    @Override
    public void roundJsonGeneratorFinish(long goldAmountWin, long goldAmountLoseCombat,
                                         int amountPointsTitleWinOrLose,long experienceGain,
                                         long goldGain,int diamondsGain, boolean levelUp) {

        ObjectNode round = createBasicRoundFinishNode(
                0,0,0,false,
                goldAmountWin, goldAmountLoseCombat);

        if (this.getUser().getHp() > 0) {
            round.put("win", this.getUser().getUsername());
            round.put("lose", defender.getUsername());
        } else {
            round.put("win", defender.getUsername());
            round.put("lose", this.getUser().getUsername());
        }

        this.getHistoryCombat().add(round);
    }
}
