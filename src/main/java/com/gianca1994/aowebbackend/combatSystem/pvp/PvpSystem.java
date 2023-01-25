package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.gianca1994.aowebbackend.combatSystem.GenericFunctions;
import com.gianca1994.aowebbackend.resources.guild.Guild;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.UserQuest;

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

        PvpModel pvpModel = new PvpModel(new ArrayList<>(), attacker, defender);

        Guild guildAttacker, guildDefender;
        long goldAmountWin = 0, goldQuestGain = 0, goldLoseForLoseCombat = 0;
        short diamondsQuestGain = 0;
        int roundCounter = 0, attackerDmg = 0, defenderDmg = 0;
        boolean stopPvP = false;
        int mmrWinAndLose = pvpUserVsUser.calculatePointsTitleWinOrLose(defender);

        do {
            roundCounter++;
            // Calculate the damage to the attacker and defender.
            if (attacker.getRole().getRoleName().equals("ADMIN")) attackerDmg = 9999999;
            else attackerDmg = genericFunctions.getUserDmg(attacker, defender.getDefense());

            defenderDmg = genericFunctions.getUserDmg(defender, attacker.getDefense());

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
                    if (defender.getGuildName() != null) {
                        guildDefender = guildRepository.findByName(defender.getGuildName());
                        if (guildDefender != null) {
                            if (defender.getTitlePoints() >= mmrWinAndLose) {
                                guildDefender.setTitlePoints(guildDefender.getTitlePoints() - mmrWinAndLose);
                                guildRepository.save(guildDefender);
                            } else {
                                guildDefender.setTitlePoints(0);
                                guildRepository.save(guildDefender);
                            }
                        }
                    }

                    // Check status title for the attacker and defender.
                    attacker.checkStatusTitlePoints(titleRepository);
                    defender.checkStatusTitlePoints(titleRepository);

                    // Add the history of the combat.
                    defender.setPvpLosses(defender.getPvpLosses() + 1);
                    attacker.setPvpWins(attacker.getPvpWins() + 1);

                    for (UserQuest quest : attacker.getUserQuests()) {
                        if (Objects.equals(quest.getQuest().getName().toLowerCase(), "player")  &&
                                quest.getAmountUserKill() < quest.getQuest().getUserKillAmountNeeded()) {
                            quest.setAmountUserKill(quest.getAmountUserKill() + 1);
                            attacker.getUserQuests().add(quest);
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
            pvpModel.roundJsonGenerator(roundCounter, attackerDmg, defenderDmg);

        } while (pvpUserVsUser.checkBothUsersAlive(attacker, defender));

        pvpModel.roundJsonGeneratorFinish(goldAmountWin, goldLoseForLoseCombat, mmrWinAndLose,
                0, 0, 0, false);

        return pvpModel;
    }
}
