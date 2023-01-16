package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;

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
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (Math.floor(Math.random() * (npc.getGiveMaxExp() - npc.getGiveMinExp() + 1) + npc.getGiveMinExp())) * SvConfig.EXPERIENCE_MULTIPLIER;
    }

    public boolean checkUserLevelUp(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has leveled up.
         * @param User user
         * @return boolean
         */
        if (user.getLevel() >= SvConfig.LEVEL_MAX) return false;
        return user.getExperience() >= user.getExperienceToNextLevel();
    }

    public short userLevelUp(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of leveling up the user.
         * @param User user
         * @return short
         */
        return (short) (user.getLevel() + 1);
    }

    public long userLevelUpNewNextExpToLevel(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the new experience
         * to next level.
         * @param User user
         * @return long
         */
        if (user.getLevel() < 10) return (long) Math.ceil(user.getExperienceToNextLevel() * 1.25);
        else if (user.getLevel() >= 10 && user.getLevel() < 150)
            return (long) Math.ceil(user.getExperienceToNextLevel() * 1.125);
        return (long) Math.ceil(user.getExperienceToNextLevel() * 1.025);

    }

    public boolean checkIfNpcDied(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the npc has died.
         * @param Npc npc
         * @return boolean
         */
        return npc.getHp() <= 0;
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

    public int freeSkillPointsAdd(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding the free skill points.
         * @param User user
         * @return int
         */
        return user.getFreeSkillPoints() + SvConfig.FREE_SKILL_POINTS_PER_LEVEL;
    }

    public boolean checkUserAndNpcAlive(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user and the npc are alive.
         * @param User user
         * @param Npc npc
         * @return boolean
         */
        return user.getHp() > 0 && npc.getHp() > 0;
    }

    public boolean chanceDropDiamonds() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the chance of dropping diamonds.
         * @return boolean
         */
        return ((Math.random() * 100) + 1) > (100 - SvConfig.DIAMOND_DROP_CHANCE_PERCENTAGE);
    }

    public int amountOfDiamondsDrop() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the amount of diamonds that will be dropped.
         * @return int
         */
        return (int) (Math.random() * SvConfig.MAXIMUM_AMOUNT_DIAMONDS_DROP) + 1;
    }

    public ObjectNode roundJsonGeneratorUserVsNpc(
            User user,
            Npc npc,
            int roundCounter,
            int userDmg,
            int npcDmg) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of generating the json for the round.
         * @param User user
         * @param Npc npc
         * @param int roundCounter
         * @param int userDmg
         * @param int npcDmg
         * @return ObjectNode
         */
        ObjectNode round = new ObjectMapper().createObjectNode();
        round.put("round", roundCounter);
        round.put("userLife", user.getHp());
        round.put("NpcLife", npc.getHp());
        round.put("userDmg", userDmg);
        round.put("NpcDmg", npcDmg);
        return round;
    }

    public ObjectNode roundJsonGeneratorUserVsNpcFinish(
            User user,
            Npc npc,
            long userExperienceGain,
            long userGoldGain,
            int diamondsGain,
            boolean userLevelUp) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of generating the json for the round.
         * @param User user
         * @param Npc npc
         * @param long userExperienceGain
         * @param long userGoldGain
         * @param int diamondsGain
         * @param boolean userLevelUp
         * @return ObjectNode
         */
        ObjectNode round = new ObjectMapper().createObjectNode();

        if (user.getHp() > 0) {
            round.put("win", user.getUsername());
            round.put("lose", npc.getName());
        } else {
            round.put("win", npc.getName());
            round.put("lose", user.getUsername());
        }
        if (userExperienceGain > 0) round.put("userExperienceGain", userExperienceGain);
        if (userGoldGain > 0) round.put("goldAmountWin", userGoldGain);
        if (diamondsGain > 0) round.put("diamondsAmountWin", diamondsGain);
        if (userLevelUp) round.put("levelUp", true);

        return round;
    }
}
