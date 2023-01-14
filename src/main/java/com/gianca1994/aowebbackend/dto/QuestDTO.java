package com.gianca1994.aowebbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestDTO {
    private String name;
    private String description;
    private String nameNpcKill;
    private int npcKillAmountNeeded;
    private long giveExp;
    private long giveGold;
}
