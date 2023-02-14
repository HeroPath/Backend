package com.gianca1994.aowebbackend.resources.quest.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of validating the data of a quest.
 */

public class QuestServiceValidator {

    public void questFound(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a quest exists.
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict(QuestConst.QUEST_NOT_FOUND);
    }

    public void questExist(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a quest exists.
         * @param boolean exist
         * @return void
         */
        if (exist) throw new Conflict(QuestConst.QUEST_ALREADY_EXISTS);
    }

    public void userQuestFound(UserQuest userQuest) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user quest exists.
         * @param UserQuest userQuest
         * @return void
         */
        if (userQuest == null) throw new NotFound(QuestConst.USER_QUEST_NOT_FOUND);
    }

    public void userFound(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user exists.
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict(QuestConst.USER_NOT_FOUND);
    }

    public void validPage(int page) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a page exists.
         * @param int page
         * @return void
         */
        if (page < 0) throw new NotFound(QuestConst.PAGE_NOT_AVAILABLE);
    }

    public void checkDtoSaveQuest(QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating the data of a quest.
         * @param QuestDTO quest
         * @return void
         */
        if (quest.getName().isEmpty()) throw new Conflict(QuestConst.NAME_EMPTY);
        if (quest.getNameNpcKill().isEmpty()) throw new Conflict(QuestConst.NAME_NPC_KILL_EMPTY);
        if (quest.getNpcAmountNeed() < 0) throw new Conflict(QuestConst.NPC_KILL_AMOUNT_LT0);
        if (quest.getUserAmountNeed() < 0) throw new Conflict(QuestConst.USER_KILL_AMOUNT_LT0);
        if (quest.getGiveExp() < 0) throw new Conflict(QuestConst.GIVE_EXP_LT0);
        if (quest.getGiveGold() < 0) throw new Conflict(QuestConst.GIVE_GOLD_LT0);
        if (quest.getGiveDiamonds() < 0) throw new Conflict(QuestConst.GIVE_DIAMONDS_LT0);
    }

    public void checkUserMaxQuests(int amountQuests) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user has reached the maximum number of quests.
         * @param int amountQuests
         * @return void
         */
        if (amountQuests >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict(QuestConst.MAX_ACTIVE_QUESTS);
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
            throw new Conflict(QuestConst.QUEST_ALREADY_ACCEPTED);
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
        if (amountKill < amountNeeded) throw new Conflict(QuestConst.QUEST_AMOUNT_CHECK);
    }

    public void questAlreadyCompleted(long userQuestId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of validating if a user has already completed a quest.
         * @param long userQuestId
         * @return void
         */
        if (userQuestId == 0) throw new Conflict(QuestConst.QUEST_ALREADY_COMPLETED);
    }
}
