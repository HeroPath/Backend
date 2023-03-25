package com.gianca1994.heropathbackend.resources.quest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for Quest
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestDTO {
    private String name;
    private String description;
    private int levelRequired;
    private String nameNpcKill;
    private int npcAmountNeed;
    private int userAmountNeed;
    private int giveExp;
    private long giveGold;
    private short giveDiamonds;
}
