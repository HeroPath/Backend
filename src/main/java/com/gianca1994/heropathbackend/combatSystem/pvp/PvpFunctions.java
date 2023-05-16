package com.gianca1994.heropathbackend.combatSystem.pvp;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.guild.Guild;
import com.gianca1994.heropathbackend.resources.guild.GuildRepository;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.*;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the functions of the pvp.
 */

public class PvpFunctions {
    public long getUserGoldThief(long userGold) {
        return (long) (userGold * SvConfig.PVP_GOLD_WIN_RATE);
    }

    public long getUserGoldAmountLose(User user) {
        return user.getGold() - getUserGoldThief(user.getGold());
    }

    public long getUserGoldLoseForLoseCombat(User user) {
        return (long) (user.getGold() * SvConfig.PVP_GOLD_LOSS_RATE);
    }

    public boolean checkBothUsersAlive(User attacker, User defender) {
        return attacker.getHp() > 0 && defender.getHp() > 0;
    }

    public int calculatePointsTitleWinOrLose(User user) {
        int pointsWinOrLose = (int) Math.floor(Math.random() * (SvConfig.PVP_MAX_RATE_POINT_TITLE - SvConfig.PVP_MIN_RATE_POINT_TITLE + 1) + SvConfig.PVP_MIN_RATE_POINT_TITLE);
        if (user.getTitlePoints() >= pointsWinOrLose) return pointsWinOrLose;
        else if (user.getTitlePoints() > 0) return user.getTitlePoints();
        else return pointsWinOrLose / 2;
    }

    public void updateGuilds(User attacker, User defender, GuildRepository guildRepository, int mmrWinAndLose) {
        String attackGuildName = attacker.getGuildName();
        String defGuildName = defender.getGuildName();
        Map<String, Guild> guilds = new HashMap<>();

        if (!Objects.equals(attackGuildName, "")) {
            guilds.put(attackGuildName, guildRepository.findByName(attackGuildName));
            Guild guildAttack = guilds.get(attackGuildName);
            if (guildAttack != null) guildAttack.setTitlePoints(guildAttack.getTitlePoints() + mmrWinAndLose);
        }

        if (!Objects.equals(defGuildName, "")) {
            guilds.put(defGuildName, guildRepository.findByName(defGuildName));
            Guild guildDef = guilds.get(defGuildName);
            if (guildDef != null)
                if (guildDef.getTitlePoints() - mmrWinAndLose >= 0)
                    guildDef.setTitlePoints(guildDef.getTitlePoints() - mmrWinAndLose);
        }
        guildRepository.saveAll(guilds.values());
    }

    public void updateQuests(User user) {
        Set<UserQuest> userQuests = user.getUserQuests();
        for (UserQuest quest : userQuests) {
            if (quest.getQuest().getNameNpcKill().equalsIgnoreCase("player")
                    && quest.getUserAmountNeed() < quest.getQuest().getUserAmountNeed()) {
                quest.setUserAmountNeed(quest.getUserAmountNeed() + 1);
                break;
            }
        }
    }

    public void updateStatsUserWin(User user, User attacked, long goldAmountWin, int mmrWinAndLose) {
        attacked.setHp(0);
        user.setGold(user.getGold() + goldAmountWin);
        attacked.setGold(attacked.getGold() - goldAmountWin);
        attacked.setPvpLosses(attacked.getPvpLosses() + 1);
        user.setPvpWins(user.getPvpWins() + 1);
        user.addTitlePoints(mmrWinAndLose);
        attacked.removeTitlePoints(mmrWinAndLose);
    }

    public void updateStatsUserLose(User user, User attacked, long goldLoseForLoseCombat, int mmrWinAndLose) {
        user.setHp(0);
        user.setGold(user.getGold() - goldLoseForLoseCombat);
        user.removeTitlePoints(mmrWinAndLose);
        user.setPvpLosses(attacked.getPvpLosses() + 1);
        attacked.setPvpWins(user.getPvpWins() + 1);
    }
}
