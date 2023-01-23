package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.guild.Guild;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.quest.Quest;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the functions that are used in the PvpCombatSystem.
 */
public class PvpSystem {

    public static PvpModel PvpUserVsUser(User attacker,
                                         User defender,
                                         TitleRepository titleRepository,
                                         GuildRepository guildRepository) {
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

        Guild guildAttacker, guildDefender;
        long goldAmountWin = 0, goldQuestGain = 0, goldLoseForLoseCombat = 0;
        short diamondsQuestGain = 0;
        int roundCounter = 0;
        boolean stopPvP = false;
        int mmrWinAndLose = pvpUserVsUser.calculatePointsTitleWinOrLose();

        do {
            roundCounter++;
            // Calculate the damage to the attacker and defender.
            int attackerDmg = genericFunctions.getUserDmg(attacker, defender.getDefense());
            int defenderDmg = genericFunctions.getUserDmg(defender, attacker.getDefense());

            if (!stopPvP) {
                defender.setHp(genericFunctions.userReceiveDmg(defender, attackerDmg));

                // Check if the defender has died.
                if (genericFunctions.checkIfUserDied(defender)) {
                    defender.setHp(0);
                    defenderDmg = 0;
                    stopPvP = true;

                    // Add points to the attacker and defender.
                    attacker.addTitlePoints(mmrWinAndLose);
                    defender.removeTitlePoints(mmrWinAndLose);

                    if (attacker.getGuildName() != null) {
                        guildAttacker = guildRepository.findByName(attacker.getGuildName());
                        if (guildAttacker != null) {
                            guildAttacker.setTitlePoints(guildAttacker.getTitlePoints() + mmrWinAndLose);
                            guildRepository.save(guildAttacker);
                        }
                    }
                    if (defender.getGuildName() != null){
                        guildDefender = guildRepository.findByName(defender.getGuildName());
                        if (guildDefender != null) {
                            guildDefender.setTitlePoints(guildDefender.getTitlePoints() - mmrWinAndLose);
                            guildRepository.save(guildDefender);
                        }
                    }

                    // Check status title for the attacker and defender.
                    attacker.checkStatusTitlePoints(titleRepository);
                    defender.checkStatusTitlePoints(titleRepository);

                    // Add the history of the combat.
                    defender.setPvpLosses(defender.getPvpLosses() + 1);
                    attacker.setPvpWins(attacker.getPvpWins() + 1);

                    for (Quest quest : attacker.getQuests()) {
                        if (Objects.equals(quest.getNameNpcKill(), "player")) {
                            quest.setUserKillAmount(quest.getUserKillAmount() + 1);
                            attacker.getQuests().add(quest);

                            if (quest.getUserKillAmount() >= quest.getUserKillAmountNeeded()) {
                                goldQuestGain = quest.getGiveGold();
                                diamondsQuestGain = (short) (Math.random() * 6);
                                attacker.getQuests().remove(quest);
                            }
                        }

                    }
                    attacker.setDiamond(attacker.getDiamond() + diamondsQuestGain);

                    goldAmountWin = pvpUserVsUser.getUserGoldAmountWin(defender) + goldQuestGain;
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
