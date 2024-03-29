package com.gianca1994.heropathbackend.resources.quest;

import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.heropathbackend.resources.quest.dto.response.QuestListDTO;
import com.gianca1994.heropathbackend.resources.quest.utilities.FilterQuest;
import com.gianca1994.heropathbackend.resources.user.*;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuestRepository;
import com.gianca1994.heropathbackend.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the business logic of the quest.
 */

@Service
public class QuestService {

    Validator validate = new Validator();

    @Autowired
    private QuestRepository questR;

    @Autowired
    private UserRepository userR;

    @Autowired
    private UserQuestRepository userQuestR;

    public QuestListDTO getQuests(String username) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting all the quests.
         * @param String username
         * @return QuestListDTO
         */
        validate.userExist(userR.existsByUsername(username));
        User user = userR.findByUsername(username);
        List<Quest> allQuestsAvailable = questR.findAllAvailableQuestsForUser(user.getLevel());

        FilterQuest pageFilterM = new FilterQuest(allQuestsAvailable, userQuestR.findByUserUsername(username));
        pageFilterM.unacceptedQuests();
        pageFilterM.acceptedQuests();
        return new QuestListDTO(pageFilterM.getAcceptedQuests(), pageFilterM.getUnacceptedQuests());
    }

    public Quest getQuestByName(String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        validate.questExist(questR.existsByName(name));
        return questR.findByName(name);
    }

    public void saveQuest(QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of saving a quest.
         * @param QuestDTO quest
         * @return none
         */
        validate.questAlreadyExist(questR.existsByName(quest.getName()));
        validate.dtoSaveQuest(quest);
        questR.save(
                new Quest(
                        quest.getName(), quest.getDescription(), quest.getLevelRequired(),
                        quest.getNameNpcKill().toLowerCase(), quest.getNpcAmountNeed(), quest.getUserAmountNeed(),
                        quest.getGiveExp(), quest.getGiveGold(), quest.getGiveDiamonds()
                )
        );
    }

    public void deleteQuest(String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of deleting a quest.
         * @param String name
         * @return none
         */
        validate.questExist(questR.existsByName(name));
        Quest quest = questR.findByName(name);
        questR.delete(quest);
    }

    public void acceptQuest(String username, String nameQuest) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of accepting a quest.
         * @param String username
         * @param String nameQuest
         * @return none
         */
        checkUserAndQuestExist(username, nameQuest);

        List<UserQuest> userQuests = userQuestR.findByUserUsername(username);
        validate.maxActiveQuest(userQuests.size());
        validate.questAccepted(userQuests, nameQuest);

        User user = userR.findByUsername(username);
        Quest quest = questR.findByName(nameQuest);

        validate.questLvlMin(user.getLevel(), quest.getLevelRequired());

        UserQuest userQuest = new UserQuest();
        userQuest.setUser(user);
        userQuest.setQuest(quest);
        userQuest.setNpcAmountNeed(0);
        userQuest.setUserAmountNeed(0);
        userQuestR.save(userQuest);
    }

    public Quest completeQuest(String username, String nameQuest) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of completing a quest.
         * @param String username
         * @param String nameQuest
         * @return Quest
         */
        checkUserAndQuestExist(username, nameQuest);

        User user = userR.findByUsername(username);
        Quest quest = questR.findByName(nameQuest);
        UserQuest userQuest = userQuestR.findByUserUsernameAndQuestName(username, nameQuest);
        validate.userQuestExist(userQuest);
        validate.questCompleted(userQuest.getNpcAmountNeed(), quest.getNpcAmountNeed());
        validate.questCompleted(userQuest.getUserAmountNeed(), quest.getUserAmountNeed());

        user.setExperience(user.getExperience() + quest.getGiveExp());
        user.setGold(user.getGold() + quest.getGiveGold());
        user.setDiamond(user.getDiamond() + quest.getGiveDiamonds());
        user.userLevelUp();

        validate.alreadyCompleted(userQuest.getId());
        userQuestR.delete(userQuest);
        user.getUserQuests().remove(userQuest);
        userR.save(user);
        return userQuest.getQuest();
    }

    public void cancelQuest(String username, String nameQuest) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of canceling a quest.
         * @param String username
         * @param String nameQuest
         * @return none
         */
        checkUserAndQuestExist(username, nameQuest);

        UserQuest userQuest = userQuestR.findByUserUsernameAndQuestName(username, nameQuest);
        validate.userQuestExist(userQuest);
        userQuestR.delete(userQuest);
    }

    private void checkUserAndQuestExist(String username, String nameQuest) throws Conflict {
        validate.userExist(userR.existsByUsername(username));
        validate.questExist(questR.existsByName(nameQuest));
    }
}