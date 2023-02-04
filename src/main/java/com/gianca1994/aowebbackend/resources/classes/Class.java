package com.gianca1994.aowebbackend.resources.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author: Gianca1994
 * Explanation: This class is used to store the data of the classes
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    private String name;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;
    private int luck;
    private int minDmgModifier;
    private int maxDmgModifier;
    private int maxHpModifier;
    private int defenseModifier;
    private int evasionModifier;
    private float criticalModifier;

}
