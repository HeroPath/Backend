package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.jwt.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.aowebbackend.resources.quest.dto.response.QuestListDTO;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/quests")
public class QuestController {

    @Autowired
    private QuestService questService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public QuestListDTO getQuests(@RequestHeader(value = "Authorization") String token,
                                  @RequestParam("page") int page) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting all the quests.
         * @param String token
         * @return List<ObjectNode>
         */
        return questService.getQuests(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                page
        );
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Quest getQuestByName(@PathVariable String name) throws Conflict {
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
    public void saveQuest(@RequestBody QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to save a quest.
         * @param QuestDTO quest
         * @return none
         */
        questService.saveQuest(quest);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteQuest(@PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of deleting a quest.
         * @param String name
         * @return none
         */
        questService.deleteQuest(name);
    }

    @PostMapping("/accept")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void acceptQuest(@RequestHeader(value = "Authorization") String token,
                            @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to accept a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        questService.acceptQuest(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                nameRequestDTO
        );
    }

    @PostMapping("/complete")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Quest completeQuest(@RequestHeader(value = "Authorization") String token,
                               @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to complete a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        return questService.completeQuest(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                nameRequestDTO
        );
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void cancelQuest(@RequestHeader(value = "Authorization") String token,
                            @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to cancel a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        questService.cancelQuest(
                jwtTokenUtil.getUsernameFromToken(token.substring(7)),
                nameRequestDTO
        );
    }
}
