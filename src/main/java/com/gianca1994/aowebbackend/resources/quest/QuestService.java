package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.jwt.JWTUserDetailsService;
import com.gianca1994.aowebbackend.resources.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserService;
import com.gianca1994.aowebbackend.resources.user.dto.NameRequestDTO;
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
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtToken;

    @Autowired
    private JWTUserDetailsService jwtUserService;

    public List<Quest> getAllQuests(String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the quests.
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
                        0,
                        quest.getNpcKillAmountNeeded(),
                        0,
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

    public void acceptQuest(String token, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of accepting a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        Quest quest = questRepository.findByName(nameRequestDTO.getName());
        if (Objects.isNull(quest)) throw new NotFound("Quest not found");

        User user = userService.getProfile(token);

        if (user == null) throw new NotFound("User not found");
        if (user.getQuests().contains(quest)) throw new Conflict("You already accepted this quest");
        if (user.getQuests().size() >= 3) throw new Conflict("You can't accept more quests");

        quest.setNpcKillAmount(0);
        quest.setUserKillAmount(0);

        user.getQuests().add(quest);
        userService.updateUser(user);
    }

    public void cancelQuest(String token, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of canceling a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        Quest quest = questRepository.findByName(nameRequestDTO.getName());
        if (Objects.isNull(quest)) throw new NotFound("Quest not found");

        User user = userService.getProfile(token);

        if (user == null) throw new NotFound("User not found");
        if (!user.getQuests().contains(quest)) throw new Conflict("You didn't accept this quest");

        user.getQuests().remove(quest);
        userService.updateUser(user);
    }
}