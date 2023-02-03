package com.gianca1994.aowebbackend.resources.user.dto.queyModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAttributes {
    @JsonIgnore
    private String aClass;
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

    public void addStat(String stat) {
        stat = stat.toLowerCase();
        switch (stat) {
            case "strength":
                this.strength++;
                break;
            case "dexterity":
                this.dexterity++;
                break;
            case "vitality":
                this.vitality++;
                break;
            case "intelligence":
                this.intelligence++;
                break;
            case "luck":
                this.luck++;
                break;
            default:
                throw new IllegalArgumentException("Invalid stat name");
        }
        this.freeSkillPoints--;
    }

    public void updateStats() {
        Class aClass;
        if (this.getAClass().equals(ModifConfig.MAGE.getName())) {
            aClass = ModifConfig.MAGE;
            this.minDmg = this.intelligence * aClass.getMinDmgModifier();
            this.maxDmg = this.intelligence * aClass.getMaxDmgModifier();
            this.maxHp = this.vitality * aClass.getMaxHpModifier();
            this.defense = this.strength * aClass.getDefenseModifier();
            this.evasion = this.dexterity * aClass.getEvasionModifier();
            this.criticalChance = this.luck * aClass.getCriticalModifier() > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : this.luck * aClass.getCriticalModifier();
        } else if (this.getAClass().equals(ModifConfig.WARRIOR.getName())) {
            aClass = ModifConfig.WARRIOR;
            this.minDmg = this.strength * aClass.getMinDmgModifier();
            this.maxDmg = this.strength * aClass.getMaxDmgModifier();
            this.maxHp = this.vitality * aClass.getMaxHpModifier();
            this.defense = this.intelligence * aClass.getDefenseModifier();
            this.evasion = this.dexterity * aClass.getEvasionModifier();
            this.criticalChance = this.luck * aClass.getCriticalModifier() > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : this.luck * aClass.getCriticalModifier();
        } else if (this.getAClass().equals(ModifConfig.ARCHER.getName())) {
            aClass = ModifConfig.ARCHER;
            this.minDmg = this.dexterity * aClass.getMinDmgModifier();
            this.maxDmg = this.dexterity * aClass.getMaxDmgModifier();
            this.maxHp = this.vitality * aClass.getMaxHpModifier();
            this.defense = this.intelligence * aClass.getDefenseModifier();
            this.evasion = this.strength * aClass.getEvasionModifier();
            this.criticalChance = this.luck * aClass.getCriticalModifier() > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : this.luck * aClass.getCriticalModifier();
        }
    }
}

