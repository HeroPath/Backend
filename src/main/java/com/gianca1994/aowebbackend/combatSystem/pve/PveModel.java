package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.CombatModel;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.*;

import java.util.ArrayList;


/**
 * @Author: Gianca1994
 * Explanation: PveSystem
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PveModel extends CombatModel {

    private Npc npc;

    public PveModel(ArrayList<ObjectNode> historyCombat, User user, Npc npc) {
        super(historyCombat, user);
        this.npc = npc;
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
        round.put("NpcLife", npc.getHp());
        round.put("NpcDmg", defenderDmg);
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
         * @param int diamondsGain
         * @param boolean levelUp
         * @return none
         */
        ObjectNode round = createBasicRoundFinishNode(
                experienceGain, goldGain, diamondsGain, levelUp,
                0, 0);

        if (this.getUser().getHp() > 0) {
            round.put("win", this.getUser().getUsername());
            round.put("lose", npc.getName());
        } else {
            round.put("win", npc.getName());
            round.put("lose", this.getUser().getUsername());
        }
        this.getHistoryCombat().add(round);
    }
}
