package com.gianca1994.aowebbackend.resources.quest.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.List;

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

    public void userQuestFound(UserQuest userQuest) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user quest exists.
         * @param UserQuest userQuest
         * @return void
         */
        if (userQuest == null) throw new NotFound("You don't have this quest");
    }

    public void userFound(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user exists.
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict("User not found");
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

    public void checkUserMaxQuests(int amountQuests) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user has reached the maximum number of quests.
         * @param int amountQuests
         * @return void
         */
        if (amountQuests >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict("You can't accept more than 3 quests");
    }

    public void checkQuestAccepted(List<UserQuest> userQuests, String questName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user has already accepted a quest.
         * @param List<UserQuest> userQuests
         * @param String questName
         * @return void
         */
        if (userQuests.stream().anyMatch(userQuest -> userQuest.getQuest().getName().equals(questName))) {
            throw new Conflict("You already accepted this quest");
        }
    }

    public void checkQuestCompleted(int amountKill, int amountNeeded) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user has completed a quest.
         * @param int amountKill
         * @param int amountNeeded
         * @return void
         */
        if (amountKill < amountNeeded) throw new Conflict("You have not killed enough NPCs or users");
    }

    public void questAlreadyCompleted(long userQuestId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user has already completed a quest.
         * @param long userQuestId
         * @return void
         */
        if (userQuestId == 0) throw new Conflict("You already completed this quest");
    }
}
