package com.gianca1994.aowebbackend.combatSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;

public class PveUserVsNpc {

    private final int EXPERIENCE_MULTIPLIER = 1;
    private final int GOLD_MULTIPLIER = 1;
    private final int FREE_SKILL_POINTS_PER_LEVEL = 3;
    private final int DIAMOND_DROP_CHANCE_PERCENTAGE = 5;
    private final int MAXIMUM_AMOUNT_DIAMONDS_DROP = 5;

    public int calculateNpcDmg(Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the npc.
         * @param Npc npc
         * @return int
         */
        return (int) (Math.random() * (npc.getMaxDmg() - npc.getMinDmg())) + npc.getMinDmg();
    }

    public long CalculateUserExperienceGain(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the experience gain.
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (user.getExperience() + ((Math.random() * (npc.getGiveMaxExp() - npc.getGiveMinExp())) + npc.getGiveMinExp()) * EXPERIENCE_MULTIPLIER);
    }

    public boolean checkUserLevelUp(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has leveled up.
         * @param User user
         * @return boolean
         */
        if (user.getLevel() >= 150) return false;
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
        if (user.getLevel() < 10) return user.getExperienceToNextLevel() * 2;
        return (long) (user.getExperienceToNextLevel() * 1.2);

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

    public long calculateUserGoldGain(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold gain.
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (user.getGold() + ((Math.random() * (npc.getGiveMaxGold() - npc.getGiveMinGold())) + npc.getGiveMinGold()) * GOLD_MULTIPLIER);
    }

    public int freeSkillPointsAdd(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of adding the free skill points.
         * @param User user
         * @return int
         */
        return user.getFreeSkillPoints() + FREE_SKILL_POINTS_PER_LEVEL;
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
        return ((Math.random() * 100) + 1) > (100 - DIAMOND_DROP_CHANCE_PERCENTAGE);
    }

    public int amountOfDiamondsDrop() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the amount of diamonds that will be dropped.
         * @return int
         */
        return (int) (Math.random() * MAXIMUM_AMOUNT_DIAMONDS_DROP) + 1;
    }

    public ObjectNode roundJsonGeneratorUserVsNpc(User user, Npc npc,
                                                  int roundCounter, int userDmg, int npcDmg) {
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
}
