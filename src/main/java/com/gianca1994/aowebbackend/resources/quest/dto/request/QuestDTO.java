package com.gianca1994.aowebbackend.resources.quest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to represent a quest in the database.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestDTO {
    private String name;
    private String nameNpcKill;
    private int npcKillAmountNeeded;
    private int userKillAmountNeeded;
    private long giveExp;
    private long giveGold;
    private short giveDiamonds;
}
