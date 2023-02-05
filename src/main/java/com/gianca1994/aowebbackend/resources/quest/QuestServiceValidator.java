package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.userRelations.UserQuest;

import java.util.List;
import java.util.Objects;

public class QuestServiceValidator {

    public void getQuestByName(Quest quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting a quest by name.
         * @param Quest quest
         * @return void
         */
        if (quest == null) throw new Conflict("Quest not found");
    }

    public void saveQuest(Quest checkQuest, QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving a quest.
         * @param Quest checkQuest
         * @param QuestDTO quest
         * @return void
         */
        if (checkQuest != null) throw new Conflict("Quest already exists");
        if (Objects.equals(quest.getName(), "")) throw new Conflict("Name cannot be empty");
        if (Objects.equals(quest.getNameNpcKill(), "")) throw new Conflict("Name NPC Kill cannot be empty");
        if (quest.getNpcKillAmountNeeded() < 0) throw new Conflict("NPC Kill Amount Needed cannot be negative");
        if (quest.getUserKillAmountNeeded() < 0) throw new Conflict("User Kill Amount Needed cannot be negative");
        if (quest.getGiveExp() < 0) throw new Conflict("Experience cannot be negative");
        if (quest.getGiveGold() < 0) throw new Conflict("Gold cannot be negative");
        if (quest.getGiveDiamonds() < 0) throw new Conflict("Diamonds cannot be negative");
    }

    public void deleteQuest(Quest quest) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of deleting a quest.
         * @param Quest quest
         * @return void
         */
        if (quest == null) throw new NotFound("Quest not found");
    }

    public void acceptQuest(User user, List<UserQuest> userQuests, Quest quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of accepting a quest.
         * @param User user
         * @param List<UserQuest> userQuests
         * @param Quest quest
         * @return void
         */
        if (user == null) throw new NotFound("User not found");
        if (userQuests.size() >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict("You can't accept more than 3 quests");
        if (quest == null) throw new NotFound("Quest not found");

        for (UserQuest userQuest : userQuests) {
            if (Objects.equals(userQuest.getQuest().getName(), quest.getName())) {
                throw new Conflict("You already accepted this quest");
            }
        }
    }

    public void completeQuest(User user, UserQuest userQuest, Quest quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of completing a quest.
         * @param User user
         * @param UserQuest userQuest
         * @param Quest quest
         * @return void
         */
        if (user == null) throw new NotFound("User not found");
        if (quest == null) throw new NotFound("Quest not found");
        if (userQuest == null) throw new NotFound("You don't have this quest");

        if (userQuest.getAmountNpcKill() < quest.getNpcKillAmountNeeded())
            throw new Conflict("You didn't kill enough npcs");
        if (userQuest.getAmountUserKill() < quest.getUserKillAmountNeeded())
            throw new Conflict("You didn't kill enough users");
    }

    public void cancelQuest(UserQuest userQuest) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of canceling a quest.
         * @param UserQuest userQuest
         * @return void
         */
        if (userQuest == null) throw new NotFound("You don't have this quest");
    }
}
