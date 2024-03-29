package com.gianca1994.heropathbackend.resources.quest;

import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.heropathbackend.resources.quest.dto.request.QuestDTO;
import com.gianca1994.heropathbackend.resources.quest.dto.response.QuestListDTO;
import com.gianca1994.heropathbackend.resources.user.dto.request.NameRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of handling the requests related to the quests.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/quests")
public class QuestController {

    @Autowired
    private QuestService questS;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public QuestListDTO getQuests(@RequestHeader(value = "Authorization") String token) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting all the quests.
         * @param String token
         * @param int page
         * @return QuestListDTO
         */
        return questS.getQuests(jwt.getUsernameFromToken(token.substring(7)));
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Quest getQuestByName(@PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of getting a quest by name.
         * @param String name
         * @return Quest
         */
        return questS.getQuestByName(name);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveQuest(@RequestBody QuestDTO quest) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of saving a quest.
         * @param QuestDTO quest
         * @return none
         */
        questS.saveQuest(quest);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteQuest(@PathVariable String name) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This function is in charge of deleting a quest.
         * @param String name
         * @return none
         */
        questS.deleteQuest(name);
    }

    @PostMapping("/accept")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void acceptQuest(@RequestHeader(value = "Authorization") String token,
                            @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to accept a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        questS.acceptQuest(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName()
        );
    }

    @PostMapping("/complete")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public Quest completeQuest(@RequestHeader(value = "Authorization") String token,
                               @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to complete a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return Quest
         */
        return questS.completeQuest(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName()
        );
    }

    @PostMapping("/cancel")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void cancelQuest(@RequestHeader(value = "Authorization") String token,
                            @RequestBody NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to cancel a quest.
         * @param String token
         * @param NameRequestDTO nameRequestDTO
         * @return none
         */
        questS.cancelQuest(
                jwt.getUsernameFromToken(token.substring(7)),
                nameRequestDTO.getName()
        );
    }
}
