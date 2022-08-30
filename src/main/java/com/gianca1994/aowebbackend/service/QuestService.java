package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.dto.QuestDTO;
import com.gianca1994.aowebbackend.model.Quest;
import com.gianca1994.aowebbackend.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService {

    @Autowired
    private QuestRepository questRepository;

    public List<Quest> getAllQuests() {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param none
         * @return List<Quest>
         */
        return questRepository.findAll();
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
                        quest.getNameNpcKill(),
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
