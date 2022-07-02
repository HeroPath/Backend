package com.gianca1994.aowebbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: ItemDTO
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String name;
    private String type;
    private short lvlMin;
    private String classRequired;
    private int price;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;
    private int luck;

    public ItemDTO(String name, String type, short lvlMin, int price, int strength, int dexterity, int intelligence, int vitality, int luck) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
    }
}
