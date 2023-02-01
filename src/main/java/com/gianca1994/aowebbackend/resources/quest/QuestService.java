package com.gianca1994.aowebbackend.resources.quest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.user.*;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.userRelations.UserQuest;
import com.gianca1994.aowebbackend.resources.user.userRelations.UserQuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQuestRepository userQuestRepository;

    QuestServiceValidator validator = new QuestServiceValidator();

    public List<ObjectNode> getQuests(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param String username
         * @return List<ObjectNode>
         */
        List<UserQuest> userQuests = userQuestRepository.findByUserUsername(username);
        List<ObjectNode> result = new ArrayList<>();

        for (Quest quest : questRepository.findAll()) {
            ObjectNode questON = new ObjectMapper().createObjectNode();
            questON.putPOJO("quest", quest);
            for (UserQuest userQuest : userQuests) {
                if (Objects.equals(userQuest.getQuest().getName(), quest.getName())) {
                    questON.put("npcKillAmount", userQuest.getAmountNpcKill());
                    questON.put("userKillAmount", userQuest.getAmountUserKill());
                    break;
                }
            }
            result.add(questON);
        }
        return result;
    }

    public Quest getQuestByName(String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        Quest quest = questRepository.findByName(name);
        validator.getQuestByName(quest);
        return quest;
    }

    public void saveQuest(QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving a quest.
         * @param Quest quest
         * @return none
         */
        Quest checkQuest = questRepository.findByName(quest.getName());
        validator.saveQuest(checkQuest, quest);
        questRepository.save(
                new Quest(
                        quest.getName(),
                        quest.getDescription(),
                        quest.getNameNpcKill().toLowerCase(),
                        quest.getNpcKillAmountNeeded(),
                        quest.getUserKillAmountNeeded(),
                        quest.getGiveExp(),
                        quest.getGiveGold(),
                        quest.getGiveDiamonds()
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
        validator.deleteQuest(quest);
        questRepository.delete(quest);
    }

    public void acceptQuest(String username, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of accepting a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        User user = userRepository.findByUsername(username);
        List<UserQuest> userQuests = userQuestRepository.findByUserUsername(username);
        Quest quest = questRepository.findByName(nameRequestDTO.getName());

        validator.acceptQuest(user, userQuests, quest);

        UserQuest userQuest = new UserQuest();
        userQuest.setUser(user);
        userQuest.setQuest(quest);
        userQuest.setAmountNpcKill(0);
        userQuest.setAmountUserKill(0);

        userQuestRepository.save(userQuest);

    }

    public Quest completeQuest(String username, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of completing a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        User user = userRepository.findByUsername(username);
        Quest quest = questRepository.findByName(nameRequestDTO.getName());
        UserQuest userQuest = userQuestRepository.findByUserUsernameAndQuestName(username, quest.getName());

        validator.completeQuest(user, userQuest, quest);

        user.setExperience(user.getExperience() + quest.getGiveExp());
        user.setGold(user.getGold() + quest.getGiveGold());
        user.setDiamond(user.getDiamond() + quest.getGiveDiamonds());
        user.userLevelUp();

        if (userQuest.getId() == null) throw new Conflict("You already completed this quest");

        userQuestRepository.delete(userQuest);
        user.getUserQuests().remove(userQuest);
        userRepository.save(user);
        return userQuest.getQuest();
    }

    public void cancelQuest(String username, NameRequestDTO nameRequestDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of canceling a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        UserQuest userQuest = userQuestRepository.findByUserUsernameAndQuestName(username, nameRequestDTO.getName());
        validator.cancelQuest(userQuest);
        userQuestRepository.delete(userQuest);
    }
}