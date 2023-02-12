package com.gianca1994.aowebbackend.combatSystem;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to create a combat model.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CombatModel {

    private static final GenericFunctions genericFunctions = new GenericFunctions();

    private ArrayList<ObjectNode> historyCombat;
    private User attacker;
    private User defender;
    private Npc npc;
    private boolean isPvP;

    public CombatModel(ArrayList<ObjectNode> historyCombat, User attacker, Npc npc) {
        this.historyCombat = historyCombat;
        this.attacker = attacker;
        this.npc = npc;
        this.isPvP = false;
    }

    public CombatModel(ArrayList<ObjectNode> historyCombat, User attacker, User defender) {
        this.historyCombat = historyCombat;
        this.attacker = attacker;
        this.defender = defender;
        this.isPvP = true;
    }

    public void roundCombat(int roundCounter, int attackerHp, int attackerDmg,
                            int defenderHp, int defenderDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round node.
         * @param int roundCounter
         * @param int attackerHp
         * @param int attackerDmg
         * @param int defenderHp
         * @param int defenderDmg
         * @return none
         */
        String attackerName = "Attacker";
        String defenderName = this.isPvP() ? "Defender" : "Npc";
        this.getHistoryCombat().add(genericFunctions.roundJsonGenerator(roundCounter, attackerName,
                attackerHp, attackerDmg, defenderName, defenderHp, defenderDmg)
        );
    }

    public void finishCombat(int attackerHp, long experienceGain, long goldGain,
                             int diamondsGain, int mmrWinAndLose, long goldAmountWin,
                             long goldAmountLoseCombat, boolean levelUp) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round finish node.
         * @param int attackerHp
         * @param long experienceGain
         * @param long goldGain
         * @param int diamondsGain
         * @param int mmrWinAndLose
         * @param long goldAmountWin
         * @param long goldAmountLoseCombat
         * @param boolean levelUp
         * @return none
         */
        String attackerName = this.getAttacker().getUsername();
        String defenderName = this.isPvP() ? this.getDefender().getUsername() : npc.getName();
        this.getHistoryCombat().add(
                genericFunctions.roundJsonGeneratorFinish(
                        attackerHp, attackerName, defenderName, mmrWinAndLose, goldAmountWin,
                        goldAmountLoseCombat, experienceGain, goldGain, diamondsGain, levelUp
                )
        );
    }
}
