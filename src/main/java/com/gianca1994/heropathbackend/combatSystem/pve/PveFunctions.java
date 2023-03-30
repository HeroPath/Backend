package com.gianca1994.heropathbackend.combatSystem.pve;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.npc.Npc;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the functions of the pve combat system.
 */

public class PveFunctions {
    public int calculateNpcDmg(Npc npc, int userDefense) {
        int npcDmg = (int) Math.floor(Math.random() * (npc.getMaxDmg() - npc.getMinDmg() + 1) + npc.getMinDmg());
        return userDefense >= npcDmg ? 0 : npcDmg - userDefense;
    }

    public long CalculateUserExperienceGain(Npc npc) {
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxExp() - npc.getGiveMinExp() + 1) + npc.getGiveMinExp()) * SvConfig.EXPERIENCE_MULTIPLIER);
    }

    public boolean checkIfNpcDied(int npcHp) {
        return npcHp <= 0;
    }

    public long calculateUserGoldGain(Npc npc) {
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxGold() - npc.getGiveMinGold() + 1) + npc.getGiveMinGold())) * SvConfig.GOLD_MULTIPLIER;
    }

    public boolean checkUserAndNpcAlive(boolean userAlive, boolean npcAlive) {
        return userAlive && npcAlive;
    }

    public boolean chanceDropDiamonds() {
        return ((Math.random() * 100) + 1) > (100 - SvConfig.DIAMOND_DROP_CHANCE_PERCENTAGE);
    }

    public int amountDiamondsDrop(User user) {
        int diamondsDrop = (int) (Math.random() * SvConfig.MAX_DIAMOND_DROP) + 1;
        user.setDiamond(user.getDiamond() + diamondsDrop);
        return diamondsDrop;
    }

    public void updateQuestProgress(User user, Npc npc) {
        Map<String, UserQuest> userQuests = user.getUserQuests().stream()
                .collect(Collectors.toMap(quest -> quest.getQuest().getNameNpcKill(), quest -> quest));

        UserQuest quest = userQuests.get(npc.getName());
        if (quest != null && !npc.getName().equals("player") &&
                quest.getNpcAmountNeed() < quest.getQuest().getNpcAmountNeed()) {
            quest.setNpcAmountNeed(quest.getNpcAmountNeed() + 1);
        }
    }

    public void updateExpAndGold(User user, long experienceGain, long goldGain) {
        user.setExperience(user.getExperience() + experienceGain);
        user.setGold(user.getGold() + goldGain);
        user.setNpcKills(user.getNpcKills() + 1);
    }
}
