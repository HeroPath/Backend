package com.gianca1994.aowebbackend.resources.quest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.user.*;
import com.gianca1994.aowebbackend.resources.user.dto.NameRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQuestRepository userQuestRepository;

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
        if (user == null) throw new NotFound("User not found");

        List<UserQuest> userQuests = userQuestRepository.findByUserUsername(username);
        if (userQuests.size() >= SvConfig.MAX_ACTIVE_QUESTS) throw new Conflict("You can't accept more than 3 quests");

        Quest quest = questRepository.findByName(nameRequestDTO.getName());
        if (quest == null) throw new NotFound("Quest not found");

        for (UserQuest userQuest : userQuests) {
            if (Objects.equals(userQuest.getQuest().getName(), quest.getName())) {
                throw new Conflict("You already accepted this quest");
            }
        }

        UserQuest userQuest = new UserQuest();
        userQuest.setUser(user);
        userQuest.setQuest(quest);
        userQuest.setAmountNpcKill(0);
        userQuest.setAmountUserKill(0);

        userQuestRepository.save(userQuest);

    }

    public void completeQuest(String username, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of completing a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound("User not found");

        Quest quest = questRepository.findByName(nameRequestDTO.getName());
        if (quest == null) throw new NotFound("Quest not found");

        UserQuest userQuest = userQuestRepository.findByUserUsernameAndQuestName(username, quest.getName());
        if (userQuest == null) throw new NotFound("You don't have this quest");

        if (userQuest.getAmountNpcKill() < quest.getNpcKillAmountNeeded())
            throw new Conflict("You didn't kill enough npcs");
        if (userQuest.getAmountUserKill() < quest.getUserKillAmountNeeded())
            throw new Conflict("You didn't kill enough users");

        user.setExperience(user.getExperience() + quest.getGiveExp());
        user.setGold(user.getGold() + quest.getGiveGold());
        user.setDiamond(user.getDiamond() + quest.getGiveDiamonds());

        user.userLevelUp();

        if (userQuest.getId() == null) throw new Conflict("You already completed this quest");

        userQuestRepository.delete(userQuest);
        user.getUserQuests().remove(userQuest);

        userRepository.save(user);
    }

    public void cancelQuest(String username, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of canceling a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        UserQuest userQuest = userQuestRepository.findByUserUsernameAndQuestName(username, nameRequestDTO.getName());
        if (userQuest == null) throw new NotFound("You don't have this quest");

        userQuestRepository.delete(userQuest);
    }
}