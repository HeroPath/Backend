package com.gianca1994.aowebbackend.resources.quest.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.quest.Quest;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PageFilterQuest {
    private int page;
    private List<Quest> allQuests;
    private List<UserQuest> userQuests;

    private List<Quest> unacceptedQuests;
    private List<Quest> unacceptedQuestsPage;
    List<ObjectNode> unacceptedResult;
    List<ObjectNode> acceptedResult;
    private int totalPages;

    public PageFilterQuest(int page, List<Quest> allQuests, List<UserQuest> userQuests) {
        this.page = page;
        this.allQuests = allQuests;
        this.userQuests = userQuests;
    }

    public void unacceptedQuests() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        this.unacceptedQuests = allQuests.stream()
                .filter(quest -> {
                    for (UserQuest userQuest : userQuests) {
                        if (userQuest.getQuest().getName().equals(quest.getName())) return false;
                    }
                    return true;
                }).collect(Collectors.toList());
        generatePage();
        unacceptedResult();
    }

    private void generatePage() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        int questPerPage = SvConfig.QUEST_PER_PAGE;
        this.totalPages = (int) Math.ceil((double) unacceptedQuests.size() / questPerPage);
        int fromIndex = page * questPerPage;
        int toIndex = Math.min(fromIndex + questPerPage, unacceptedQuests.size());
        this.unacceptedQuestsPage = unacceptedQuests.subList(fromIndex, toIndex);
    }

    public void unacceptedResult() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        this.unacceptedResult = unacceptedQuestsPage.stream()
                .map(quest -> {
                    ObjectNode questON = new ObjectMapper().createObjectNode();
                    questON.putPOJO("quest", quest);
                    return questON;
                }).collect(Collectors.toList());
    }

    public void acceptedQuests() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @return void
         */
        this.acceptedResult = userQuests.stream()
                .map(userQuest -> {
                    ObjectNode questON = new ObjectMapper().createObjectNode();
                    questON.putPOJO("quest", userQuest.getQuest());
                    questON.put("npcKillAmount", userQuest.getAmountNpcKill());
                    questON.put("userKillAmount", userQuest.getAmountUserKill());
                    return questON;
                }).collect(Collectors.toList());
    }
}
