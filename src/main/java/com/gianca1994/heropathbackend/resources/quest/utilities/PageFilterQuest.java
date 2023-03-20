package com.gianca1994.heropathbackend.resources.quest.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.quest.Quest;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of getting all the quests.
 */

@Getter
@AllArgsConstructor
public class PageFilterQuest {
    private List<Quest> allQuests;
    private List<UserQuest> userQuests;

    private List<Quest> unacceptedQuests;
    private List<Quest> unacceptedQuestsPage;
    List<ObjectNode> unacceptedResult;
    List<ObjectNode> acceptedResult;

    public PageFilterQuest(List<Quest> allQuests, List<UserQuest> userQuests) {
        this.allQuests = allQuests;
        this.userQuests = userQuests;
    }

    public void unacceptedQuests() {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        this.unacceptedQuests = allQuests.stream()
                .filter(quest -> {
                    for (UserQuest userQuest : userQuests) {
                        if (userQuest.getQuest().getName().equals(quest.getName())) return false;
                    }
                    return true;
                }).collect(Collectors.toList());
        unacceptedResult();
    }

    public void unacceptedResult() {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        this.unacceptedResult = unacceptedQuests.stream()
                .map(quest -> {
                    ObjectNode questON = new ObjectMapper().createObjectNode();
                    questON.putPOJO("quest", quest);
                    return questON;
                }).collect(Collectors.toList());
    }

    public void acceptedQuests() {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        this.acceptedResult = userQuests.stream()
                .map(userQuest -> {
                    ObjectNode questON = new ObjectMapper().createObjectNode();
                    questON.putPOJO("quest", userQuest.getQuest());
                    questON.put("npcKillAmount", userQuest.getNpcAmountNeed());
                    questON.put("userKillAmount", userQuest.getUserAmountNeed());
                    return questON;
                }).collect(Collectors.toList());
    }
}
