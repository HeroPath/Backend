package com.gianca1994.aowebbackend.resources.quest.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.quest.Quest;
import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.List;
import java.util.Objects;

public class QuestServiceValidator {

    public void questFound(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a quest exists.
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict("Quest not found");
    }

    public void questExist(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a quest exists.
         * @param boolean exist
         * @return void
         */
        if (exist) throw new Conflict("Quest already exists");
    }

    public void validPage(int page) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a page exists.
         * @param int page
         * @return void
         */
        if (page < 0) throw new NotFound("Page not available");
    }

    public void checkDtoSaveQuest(QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the data of a quest.
         * @param QuestDTO quest
         * @return void
         */
        if (quest.getName().isEmpty()) throw new Conflict("Name cannot be empty");
        if (quest.getNameNpcKill().isEmpty()) throw new Conflict("Name NPC Kill cannot be empty");
        if (quest.getNpcKillAmountNeeded() < 0) throw new Conflict("NPC Kill Amount Needed cannot be negative");
        if (quest.getUserKillAmountNeeded() < 0) throw new Conflict("User Kill Amount Needed cannot be negative");
        if (quest.getGiveExp() < 0) throw new Conflict("Experience cannot be negative");
        if (quest.getGiveGold() < 0) throw new Conflict("Gold cannot be negative");
        if (quest.getGiveDiamonds() < 0) throw new Conflict("Diamonds cannot be negative");
    }

    public void userFound(boolean exist) throws Conflict {
        /**
         *
         */
        if (!exist) throw new Conflict("User not found");
    }

    public void checkUserMaxQuests(int amountQuests) throws Conflict {
        /**
         *
         */
        if (amountQuests >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict("You can't accept more than 3 quests");
    }

    public void checkQuestAccepted(List<UserQuest> userQuests, String questName) throws Conflict {
        /**
         *
         */
        if (userQuests.stream().anyMatch(userQuest -> userQuest.getQuest().getName().equals(questName))) {
            throw new Conflict("You already accepted this quest");
        }
    }

    //////////////////////////////////////

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
