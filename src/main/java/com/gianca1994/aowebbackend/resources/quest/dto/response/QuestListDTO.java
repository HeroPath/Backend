package com.gianca1994.aowebbackend.resources.quest.dto.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestListDTO {
    private List<ObjectNode> quests;
    private int totalPages;
}
