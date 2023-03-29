package com.gianca1994.heropathbackend.resources.quest.utilities;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;
import com.gianca1994.heropathbackend.utils.Const;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of validating the data of a quest.
 */

public class QuestServiceValidator {

    public void userExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.USER.NOT_FOUND.getMsg());
    }

    public void questExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.QUEST.NOT_FOUND.getMsg());
    }

    public void questAlreadyExist(boolean alreadyExist) throws Conflict {
        if (alreadyExist) throw new Conflict(Const.QUEST.ALREADY_EXIST.getMsg());
    }

    public void userQuestExist(UserQuest userQuest) {
        if (userQuest == null) throw new NotFound(Const.QUEST.USER_QUEST_NOT_FOUND.getMsg());
    }

    public void checkDtoSaveQuest(QuestDTO quest) throws Conflict {
        if (quest.getName().isEmpty()) throw new Conflict(Const.QUEST.NAME_EMPTY.getMsg());
        if (quest.getNameNpcKill().isEmpty()) throw new Conflict(Const.QUEST.NAME_NPC_EMPTY.getMsg());
        if (quest.getNpcAmountNeed() < 0) throw new Conflict(Const.QUEST.NPC_AMOUNT_LT0.getMsg());
        if (quest.getUserAmountNeed() < 0) throw new Conflict(Const.QUEST.USER_AMOUNT_LT0.getMsg());
        if (quest.getGiveExp() < 0) throw new Conflict(Const.QUEST.GIVE_EXP_LT0.getMsg());
        if (quest.getGiveGold() < 0) throw new Conflict(Const.QUEST.GIVE_GOLD_LT0.getMsg());
        if (quest.getGiveDiamonds() < 0) throw new Conflict(Const.QUEST.GIVE_DIAMOND_LT0.getMsg());
    }

    public void checkUserMaxQuests(int amountQuests) throws Conflict {
        if (amountQuests >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict(Const.QUEST.MAX_ACTIVE.getMsg());
    }

    public void checkQuestAccepted(List<UserQuest> userQuests, String questName) throws Conflict {
        if (userQuests.stream().anyMatch(userQuest -> userQuest.getQuest().getName().equals(questName))) {
            throw new Conflict(Const.QUEST.ALREADY_ACCEPTED.getMsg());
        }
    }

    public void checkQuestCompleted(int amountKill, int amountNeeded) throws Conflict {
        if (amountKill < amountNeeded) throw new Conflict(Const.QUEST.AMOUNT_CHECK.getMsg());
    }

    public void questAlreadyCompleted(long userQuestId) throws Conflict {
        if (userQuestId == 0) throw new Conflict(Const.QUEST.ALREADY_COMPLETED.getMsg());
    }

    public void checkUserHaveLvlRequired(int userLvl, int questLvl) throws Conflict {
        if (userLvl < questLvl) throw new Conflict(Const.QUEST.LVL_NOT_ENOUGH.getMsg());
    }
}
