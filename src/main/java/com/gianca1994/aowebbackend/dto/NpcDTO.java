package com.gianca1994.aowebbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: NpcDTO
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NpcDTO {
    private String name;
    private short level;
    private Long giveMaxExp;
    private Long giveMinExp;
    private Long giveMaxGold;
    private Long giveMinGold;
    private int hp;
    private int maxHp;
    private int minDmg;
    private int maxDmg;
    private int defense;
    private String zone;
}

