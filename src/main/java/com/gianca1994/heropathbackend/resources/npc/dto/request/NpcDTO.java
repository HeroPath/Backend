package com.gianca1994.heropathbackend.resources.npc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to create a new NPC
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NpcDTO {
    private String name;
    private short level;
    private int giveMaxExp;
    private int giveMinExp;
    private Long giveMaxGold;
    private Long giveMinGold;
    private int hp;
    private int maxHp;
    private int minDmg;
    private int maxDmg;
    private int defense;
    private String zone;
}

