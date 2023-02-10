package com.gianca1994.aowebbackend.resources.item.dto.response;

import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to send the user's data to the client after an item is equipped or unequipped.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipOrUnequipDTO {
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

    public EquipOrUnequipDTO(User user) {
        this.inventory = user.getInventory();
        this.equipment = user.getEquipment();
        this.strength = user.getStrength();
        this.dexterity = user.getDexterity();
        this.intelligence = user.getIntelligence();
        this.vitality = user.getVitality();
        this.luck = user.getLuck();
        this.freeSkillPoints = user.getFreeSkillPoints();
        this.maxDmg = user.getMaxDmg();
        this.minDmg = user.getMinDmg();
        this.maxHp = user.getMaxHp();
        this.hp = user.getHp();
        this.defense = user.getDefense();
        this.evasion = user.getEvasion();
        this.criticalChance = user.getCriticalChance();
    }
}
