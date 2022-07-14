package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the functions that are used in the PvpCombatSystem.
 */
public class PvpSystem {



    public static PvpModel PvpUserVsUser(User attacker, User defender, TitleRepository titleRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of starting a combat between two users.
         * @param User attacker
         * @param User defender
         * @param TitleRepository titleRepository
         * @return PvpModel
         */
        GenericFunctions genericFunctions = new GenericFunctions();
        PvpFunctions pvpUserVsUser = new PvpFunctions();
        ArrayList<ObjectNode> historyCombat = new ArrayList<>();

        long goldAmountWin = 0;
        long goldLoseForLoseCombat = 0;

        int roundCounter = 0;
        boolean stopPvP = false;
        int mmrWinAndLose = pvpUserVsUser.calculatePointsTitleWinOrLose();

        do {
            roundCounter++;

            // Calculate the damage of the attacker and defender.
            int attackerDmg = genericFunctions.getUserDmg(attacker, defender.getDefense());
            int defenderDmg = genericFunctions.getUserDmg(defender, attacker.getDefense());

            if (!stopPvP) {
                defender.setHp(genericFunctions.userReceiveDmg(defender, attackerDmg));

                // Check if the defender has died.
                if (genericFunctions.checkIfUserDied(defender)) {
                    defender.setHp(0);
                    defenderDmg = 0;
                    stopPvP = true;

                    attacker.addTitlePoints(mmrWinAndLose);
                    attacker.checkStatusTitlePoints(titleRepository);

                    defender.removeTitlePoints(mmrWinAndLose);
                    defender.checkStatusTitlePoints(titleRepository);

                    // Add the history of the combat.
                    defender.setPvpLosses(defender.getPvpLosses() + 1);
                    attacker.setPvpWins(attacker.getPvpWins() + 1);

                    goldAmountWin = pvpUserVsUser.getUserGoldAmountWin(defender);
                    attacker.setGold(attacker.getGold() + goldAmountWin);
                    defender.setGold(pvpUserVsUser.getUserGoldAmountLose(defender));

                } else {
                    attacker.setHp(genericFunctions.userReceiveDmg(attacker, defenderDmg));

                    // Check if the attacker has died.
                    if (genericFunctions.checkIfUserDied(attacker)) {
                        attacker.setHp(0);
                        attackerDmg = 0;
                        stopPvP = true;

                        attacker.removeTitlePoints(mmrWinAndLose);
                        attacker.checkStatusTitlePoints(titleRepository);

                        // Add the history of the combat.
                        attacker.setPvpLosses(defender.getPvpLosses() + 1);
                        defender.setPvpWins(attacker.getPvpWins() + 1);
                        goldLoseForLoseCombat = pvpUserVsUser.getUserGoldLoseForLoseCombat(attacker);
                        attacker.setGold(attacker.getGold() - goldLoseForLoseCombat);
                    }
                }
            }
            historyCombat.add(pvpUserVsUser.roundJsonGeneratorUserVsUser(
                    attacker, defender, roundCounter, attackerDmg, defenderDmg));


        } while (pvpUserVsUser.checkBothUsersAlive(attacker, defender));

        historyCombat.add(pvpUserVsUser.roundJsonGeneratorUserVsUserFinish(
                attacker, defender, goldAmountWin, goldLoseForLoseCombat, mmrWinAndLose));

        return new PvpModel(attacker, defender, historyCombat);
    }
}
