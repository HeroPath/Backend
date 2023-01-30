package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserQuest;

import java.util.List;
import java.util.Objects;

public class QuestServiceValidator {

    public void acceptQuest(User user, List<UserQuest> userQuests, Quest quest) throws Conflict {
        /**
         *
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
         *
         */
        if (user == null) throw new NotFound("User not found");
        if (quest == null) throw new NotFound("Quest not found");
        if (userQuest == null) throw new NotFound("You don't have this quest");

        if (userQuest.getAmountNpcKill() < quest.getNpcKillAmountNeeded())
            throw new Conflict("You didn't kill enough npcs");
        if (userQuest.getAmountUserKill() < quest.getUserKillAmountNeeded())
            throw new Conflict("You didn't kill enough users");
    }

    public void cancelQuest(UserQuest userQuest){
        /**
         *
         */
        if (userQuest == null) throw new NotFound("You don't have this quest");
    }
}
