package com.gianca1994.aowebbackend.resources.user.dto.response;


import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEquipOrUnequipDTO {
    Inventory inventory;
    Equipment equipment;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;
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
