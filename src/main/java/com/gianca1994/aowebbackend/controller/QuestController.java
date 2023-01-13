package com.gianca1994.aowebbackend.controller;

import com.gianca1994.aowebbackend.dto.QuestDTO;
import com.gianca1994.aowebbackend.model.Quest;
import com.gianca1994.aowebbackend.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/quests")
public class QuestController {
    @Autowired
    private QuestService questService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Quest> getAllQuests(@RequestHeader(value = "Authorization") String token) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param none
         * @return List<Quest>
         */
        return questService.getAllQuests(token);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Quest getQuestByName(@PathVariable String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        return questService.getQuestByName(name);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveQuest(@RequestBody QuestDTO quest) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving a quest.
         * @param Quest quest
         * @return none
         */
        questService.saveQuest(quest);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteQuest(@PathVariable String name) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of deleting a quest.
         * @param String name
         * @return none
         */
        questService.deleteQuest(name);
    }

}
