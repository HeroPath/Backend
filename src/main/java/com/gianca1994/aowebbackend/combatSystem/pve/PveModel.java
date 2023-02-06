package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
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
public class PveModel {

    private static final GenericFunctions genericFunctions = new GenericFunctions();

    private ArrayList<ObjectNode> historyCombat;
    private User user;
    private Npc npc;

    public void roundJsonGenerator(int roundCounter, int userHp, int userDmg, int npcHp, int npcDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round node.
         * @param int roundCounter
         * @param int attackerDmg
         * @param int defenderDmg
         * @return none
         */
        this.getHistoryCombat().add(genericFunctions.roundJsonGenerator(roundCounter, "Attacker",
                userHp, userDmg, "Npc", npcHp, npcDmg)
        );
    }

    public void roundJsonGeneratorFinish(int userHp, String userName, String npcName, long experienceGain,
                                         long goldGain, int diamondsGain, boolean levelUp) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a basic round finish node.
         * @param long experienceGain
         * @param long goldGain
         * @param int diamondsGain
         * @param boolean levelUp
         * @return none
         */
        this.getHistoryCombat().add(
                genericFunctions.roundJsonGeneratorFinish(
                    userHp, userName, npcName, 0, 0, 0, experienceGain, goldGain, diamondsGain, levelUp
                )
        );
    }
}
