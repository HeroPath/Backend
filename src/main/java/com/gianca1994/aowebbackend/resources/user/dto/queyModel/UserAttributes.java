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
        this.freeSkillPoints--;
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
        updateStats();
    }

    //********** START CALCULATE STATS **********//
    public void updateStats() {
        /**
         * @Author: Gianca1994
         * Explanation: Calculates the stats of the user
         * @param boolean fullMinHp
         * @return void
         */
        Class aClass = ModifConfig.CLASSES.stream()
                .filter(c -> c.getName().equals(this.getAClass()))
                .findFirst().orElse(null);
        if (aClass == null) return;

        if (aClass.getName().equals("mage")) setStats(this.intelligence, this.strength, this.dexterity);
        else if (aClass.getName().equals("warrior")) setStats(this.strength, this.intelligence, this.dexterity);
        else if (aClass.getName().equals("archer")) setStats(this.dexterity, this.intelligence, this.strength);

        applyModifiers(aClass);
    }

    private void setStats(int minMaxDmg, int defense, int evasion) {
        /**
         * @Author: Gianca1994
         * Explanation: Sets the stats of the user
         * @param int minMaxDmg
         * @param int defense
         * @param int evasion
         * @return void
         */
        this.minDmg = minMaxDmg;
        this.maxDmg = minMaxDmg;
        this.defense = defense;
        this.evasion = evasion;
    }

    private void applyModifiers(Class aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: Applies the modifiers of the class to the stats of the user
         * @param Class aClass
         * @return void
         */
        this.minDmg *= aClass.getMinDmgModifier();
        this.maxDmg *= aClass.getMaxDmgModifier();
        this.defense *= aClass.getDefenseModifier();
        this.evasion *= aClass.getEvasionModifier();
        this.maxHp = this.vitality * aClass.getMaxHpModifier();
        this.criticalChance = Math.min(this.luck * aClass.getCriticalModifier(), ModifConfig.MAX_CRITICAL_PERCENTAGE);
    }
    //********** END CALCULATE STATS **********//

}

