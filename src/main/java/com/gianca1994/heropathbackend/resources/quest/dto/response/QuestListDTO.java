package com.gianca1994.heropathbackend.resources.quest.dto.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for the list of quests
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestListDTO {
    private List<ObjectNode> acceptedQuests;
    private List<ObjectNode> quests;
    private int totalPages;
}
