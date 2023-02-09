package com.gianca1994.aowebbackend.resources.quest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.aowebbackend.resources.quest.dto.response.QuestListDTO;
import com.gianca1994.aowebbackend.resources.quest.utilities.QuestServiceValidator;
import com.gianca1994.aowebbackend.resources.user.*;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestService {

    QuestServiceValidator validator = new QuestServiceValidator();

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQuestRepository userQuestRepository;

    public QuestListDTO getQuests(String username, int page) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param String username
         * @param int page
         * @return List<ObjectNode>
         */
        int questPerPage = 5;
        List<Quest> allQuests = questRepository.findAll();
        List<UserQuest> userQuests = userQuestRepository.findByUserUsername(username);

        List<Quest> unacceptedQuests = allQuests.stream()
                .filter(quest -> {
                    for (UserQuest userQuest : userQuests) {
                        if (userQuest.getQuest().getName().equals(quest.getName())) return false;
                    }
                    return true;
                }).collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) unacceptedQuests.size() / questPerPage);
        int fromIndex = page * questPerPage;
        int toIndex = Math.min(fromIndex + questPerPage, unacceptedQuests.size());
        List<Quest> unacceptedQuestsPage = unacceptedQuests.subList(fromIndex, toIndex);

        List<ObjectNode> unacceptedResult = unacceptedQuestsPage.stream()
                .map(quest -> {
                    ObjectNode questON = new ObjectMapper().createObjectNode();
                    questON.putPOJO("quest", quest);
                    return questON;
                }).collect(Collectors.toList());

        List<ObjectNode> acceptedResult = userQuests.stream()
                .map(userQuest -> {
                    ObjectNode questON = new ObjectMapper().createObjectNode();
                    questON.putPOJO("quest", userQuest.getQuest());
                    questON.put("npcKillAmount", userQuest.getAmountNpcKill());
                    questON.put("userKillAmount", userQuest.getAmountUserKill());
                    return questON;
                }).collect(Collectors.toList());

        return new QuestListDTO(acceptedResult, unacceptedResult, totalPages);
    }

    public Quest getQuestByName(String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        validator.getQuestByNameOrDelete(questRepository.existsByName(name));
        return questRepository.findByName(name);
    }

    public void saveQuest(QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving a quest.
         * @param Quest quest
         * @return none
         */
        validator.saveQuest(quest, questRepository.existsByName(quest.getName()));
        questRepository.save(
                new Quest(
                        quest.getName(), quest.getNameNpcKill().toLowerCase(),
                        quest.getNpcKillAmountNeeded(), quest.getUserKillAmountNeeded(),
                        quest.getGiveExp(), quest.getGiveGold(), quest.getGiveDiamonds()
                )
        );
    }

    public void deleteQuest(String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of deleting a quest.
         * @param String name
         * @return none
         */
        validator.getQuestByNameOrDelete(questRepository.existsByName(name));

        Quest quest = questRepository.findByName(name);
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