package com.gianca1994.aowebbackend.combatSystem.pve;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Gianca1994
 * Explanation: PveFunctions
 */
public class PveFunctions {
    public int calculateNpcDmg(Npc npc, int userDefense) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the npc.
         * @param Npc npc
         * @return int
         */
        int npcDmg = (int) Math.floor(Math.random() * (npc.getMaxDmg() - npc.getMinDmg() + 1) + npc.getMinDmg());
        return userDefense >= npcDmg ? 0 : npcDmg - userDefense;
    }

    public long CalculateUserExperienceGain(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the experience gain.
         * @param Npc npc
         * @return long
         */
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxExp() - npc.getGiveMinExp() + 1) + npc.getGiveMinExp())) * SvConfig.EXPERIENCE_MULTIPLIER;
    }

    public boolean checkIfNpcDied(int npcHp) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the npc has died.
         * @param Npc npc
         * @return boolean
         */
        return npcHp <= 0;
    }

    public long calculateUserGoldGain(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold gain.
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxGold() - npc.getGiveMinGold() + 1) + npc.getGiveMinGold())) * SvConfig.GOLD_MULTIPLIER;
    }

    public boolean checkUserAndNpcAlive(boolean userAlive, boolean npcAlive) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user and the npc are alive.
         * @param boolean userAlive
         * @param boolean npcAlive
         * @return boolean
         */
        return userAlive && npcAlive;
    }

    public boolean chanceDropDiamonds() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the chance of dropping diamonds.
         * @return boolean
         */
        return ((Math.random() * 100) + 1) > (100 - SvConfig.DIAMOND_DROP_CHANCE_PERCENTAGE);
    }

    public int amountDiamondsDrop(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the amount of diamonds that will be dropped.
         * @return int
         */
        int diamondsDrop = (int) (Math.random() * SvConfig.MAXIMUM_AMOUNT_DIAMONDS_DROP) + 1;
        user.setDiamond(user.getDiamond() + diamondsDrop);
        return diamondsDrop;
    }

    public void updateQuestProgress(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of updating the quest progress.
         * @param User user
         * @param Npc npc
         * @return none
         */
        Map<String, UserQuest> userQuests = new HashMap<>();
        for (UserQuest quest : user.getUserQuests()) {
            userQuests.put(quest.getQuest().getNameNpcKill(), quest);
        }

        UserQuest quest = userQuests.get(npc.getName());
        if (quest != null && !npc.getName().equals("player") &&
                quest.getAmountNpcKill() < quest.getQuest().getNpcKillAmountNeeded()) {
            quest.setAmountNpcKill(quest.getAmountNpcKill() + 1);
        }
    }

    public void updateExpGldNpcsKilled(User user, long experienceGain, long goldGain) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of updating the experience, gold and npcs killed.
         * @param User user
         * @param long experienceGain
         * @param long goldGain
         * @return none
         */
        user.setExperience(user.getExperience() + experienceGain);
        user.setGold(user.getGold() + goldGain);
        user.setNpcKills(user.getNpcKills() + 1);
    }

}
