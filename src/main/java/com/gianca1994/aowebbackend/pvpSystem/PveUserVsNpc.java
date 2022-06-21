package com.gianca1994.aowebbackend.pvpSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.model.Npc;
import com.gianca1994.aowebbackend.model.User;

public class PveUserVsNpc {

    private final int EXPERIENCE_MULTIPLIER = 1;
    private final int GOLD_MULTIPLIER = 1;
    private final float MINIMUM_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED = 0.25f;

    public boolean checkLifeStartCombat(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has enough
         * life to start a combat.
         * @param User user
         * @return boolean
         */
        return user.getHp() < user.getMaxHp() * MINIMUM_PERCENTAGE_LIFE_ATTACK_OR_ATTACKED;
    }

    public int calculateUserDmg(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the damage that the user.
         * @param User user
         * @return int
         */
        return (int) ((int) (Math.random() * (user.getMaxDmg() - user.getMinDmg())) + user.getMinDmg());
    }

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
        long newExperienceToNextLevel = 0;

        if (user.getLevel() <= 10) {
            newExperienceToNextLevel = user.getExperienceToNextLevel() * 2;
        } else if (user.getLevel() <= 20) {
            newExperienceToNextLevel = (long) (user.getExperienceToNextLevel() * 1.5);
        } else if (user.getLevel() <= 30) {
            newExperienceToNextLevel = (long) (user.getExperienceToNextLevel() * 1.25);
        } else if (user.getLevel() <= 40) {
            newExperienceToNextLevel = (long) (user.getExperienceToNextLevel() * 1.15);
        } else if (user.getLevel() <= 50) {
            newExperienceToNextLevel = (long) (user.getExperienceToNextLevel() * 1.1);
        }
        return newExperienceToNextLevel;
    }

    public boolean checkIfUserDied(User user) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of verifying if the user has died.
         * @param User user
         * @return boolean
         */
        return user.getHp() <= 0;
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

    public long CalculateUserGoldGain(User user, Npc npc) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of calculating the gold gain.
         * @param User user
         * @param Npc npc
         * @return long
         */
        return (long) (user.getGold() + ((Math.random() * (npc.getGiveMaxGold() - npc.getGiveMinGold())) + npc.getGiveMinGold()) * GOLD_MULTIPLIER);
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
