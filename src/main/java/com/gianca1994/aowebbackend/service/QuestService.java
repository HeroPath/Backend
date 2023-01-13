package com.gianca1994.aowebbackend.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.dto.QuestDTO;
import com.gianca1994.aowebbackend.model.Quest;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.QuestRepository;
import com.gianca1994.aowebbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserService userService;

    public List<Quest> getAllQuests(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param none
         * @return List<Quest>
         */
        ArrayList<Quest> quests = (ArrayList<Quest>) questRepository.findAll();
        User user = userService.getProfile(token);
        ArrayList<Quest> questsList = new ArrayList<>();

        for (Quest quest : quests) {
            if (!user.getQuests().contains(quest)) questsList.add(quest);
        }
        return questsList;


    }

    public Quest getQuestByName(String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        return questRepository.findByName(name);
    }

    public void saveQuest(QuestDTO quest) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving a quest.
         * @param Quest quest
         * @return none
         */
        questRepository.save(
                new Quest(
                        quest.getName(),
                        quest.getDescription(),
                        quest.getNameNpcKill().toLowerCase(),
                        quest.getNpcKillAmount(),
                        quest.getGiveExp(),
                        quest.getGiveGold()
                )
        );
    }

    public void deleteQuest(String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of deleting a quest.
         * @param String name
         * @return none
         */
        Quest quest = questRepository.findByName(name);
        questRepository.delete(quest);
    }
}