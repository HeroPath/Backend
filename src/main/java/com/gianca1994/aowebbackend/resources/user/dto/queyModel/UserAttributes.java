package com.gianca1994.aowebbackend.resources.user.dto.queyModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAttributes {
    private int strength;
    private int dexterity;
    private int vitality;
    private int intelligence;
    private int luck;
    private int freeSkillPoints;
    private int maxDmg;
    private int minDmg;
    private int maxHp;
    private int hp;
    private int defense;
    private int evasion;
    private float criticalChance;
}

