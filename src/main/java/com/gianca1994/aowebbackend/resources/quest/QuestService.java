package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.aowebbackend.resources.quest.dto.response.QuestListDTO;
import com.gianca1994.aowebbackend.resources.quest.utilities.PageFilterQuest;
import com.gianca1994.aowebbackend.resources.quest.utilities.QuestServiceValidator;
import com.gianca1994.aowebbackend.resources.user.*;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService {

    QuestServiceValidator validator = new QuestServiceValidator();

    @Autowired
    private QuestRepository questR;

    @Autowired
    private UserRepository userR;

    @Autowired
    private UserQuestRepository userQuestR;

    public QuestListDTO getQuests(String username, int page) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param String username
         * @param int page
         * @return List<ObjectNode>
         */
        validator.validPage(page);
        PageFilterQuest pageFilterM = new PageFilterQuest(
                page, questR.findAll(), userQuestR.findByUserUsername(username)
        );
        pageFilterM.unacceptedQuests();
        pageFilterM.acceptedQuests();
        return new QuestListDTO(
                pageFilterM.getAcceptedResult(), pageFilterM.getUnacceptedResult(), pageFilterM.getTotalPages()
        );
    }

    public Quest getQuestByName(String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        validator.questFound(questR.existsByName(name));
        return questR.findByName(name);
    }

    public void saveQuest(QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving a quest.
         * @param Quest quest
         * @return none
         */
        validator.questExist(questR.existsByName(quest.getName()));
        validator.checkDtoSaveQuest(quest);
        questR.save(
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
        validator.questFound(questR.existsByName(name));
        Quest quest = questR.findByName(name);
        questR.delete(quest);
    }

    public void acceptQuest(String username, String nameQuest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of accepting a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        validator.userFound(userR.existsByUsername(username));
        validator.questFound(questR.existsByName(nameQuest));

        List<UserQuest> userQuests = userQuestR.findByUserUsername(username);
        validator.checkUserMaxQuests(userQuests.size());
        validator.checkQuestAccepted(userQuests, nameQuest);

        User user = userR.findByUsername(username);
        Quest quest = questR.findByName(nameQuest);
        UserQuest userQuest = new UserQuest();
        userQuest.setUser(user);
        userQuest.setQuest(quest);
        userQuest.setAmountNpcKill(0);
        userQuest.setAmountUserKill(0);
        userQuestR.save(userQuest);
    }

    public Quest completeQuest(String username, String questName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of completing a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        validator.userFound(userR.existsByUsername(username));
        validator.questFound(questR.existsByName(questName));

        User user = userR.findByUsername(username);
        Quest quest = questR.findByName(questName);
        UserQuest userQuest = userQuestR.findByUserUsernameAndQuestName(username, questName);
        validator.userQuestFound(userQuest);
        validator.checkQuestCompleted(userQuest.getAmountNpcKill(), quest.getNpcKillAmountNeeded());
        validator.checkQuestCompleted(userQuest.getAmountUserKill(), quest.getUserKillAmountNeeded());

        user.setExperience(user.getExperience() + quest.getGiveExp());
        user.setGold(user.getGold() + quest.getGiveGold());
        user.setDiamond(user.getDiamond() + quest.getGiveDiamonds());
        user.userLevelUp();

        validator.questAlreadyCompleted(userQuest.getId());
        userQuestR.delete(userQuest);
        user.getUserQuests().remove(userQuest);
        userR.save(user);
        return userQuest.getQuest();
    }

    public void cancelQuest(String username, String questName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of canceling a quest.
         * @param String username
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        validator.userFound(userR.existsByUsername(username));
        validator.questFound(questR.existsByName(questName));

        UserQuest userQuest = userQuestR.findByUserUsernameAndQuestName(username, questName);
        validator.userQuestFound(userQuest);
        userQuestR.delete(userQuest);
    }
}