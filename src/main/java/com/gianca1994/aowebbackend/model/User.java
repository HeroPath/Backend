package com.gianca1994.aowebbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


/**
 * @Author: Gianca1994
 * Explanation: Class
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String email;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private Role role;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_class",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "a_class_id",
                    referencedColumnName = "id"))
    private Class aClass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_inventory",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id",
                    referencedColumnName = "id"))
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_equipment",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id",
                    referencedColumnName = "id"))
    private Equipment equipment;

    @Column
    private short level;
    @Column
    private long experience;
    @Column
    private long experienceToNextLevel;
    @Column
    private long gold;
    @Column
    private int diamond;
    @Column
    private int maxDmg;
    @Column
    private int minDmg;
    @Column
    private int maxHp;
    @Column
    private int hp;
    @Column
    private int strength;
    @Column
    private int dexterity;
    @Column
    private int intelligence;
    @Column
    private int vitality;
    @Column
    private int luck;
    @Column
    private int freeSkillPoints;
    @Column
    private int npcKills;
    @Column
    private int pvpWins;
    @Column
    private int pvpLosses;

    public User(String username, String password, String email, Role role, Class aClass, Inventory inventory, Equipment equipment, short level, long experience, long experienceToNextLevel, long gold, int diamond, int maxDmg, int minDmg, int maxHp, int hp, int strength, int dexterity, int intelligence, int vitality, int luck, int freeSkillPoints, int npcKills, int pvpWins, int pvpLosses) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.aClass = aClass;
        this.inventory = inventory;
        this.equipment = equipment;
        this.level = level;
        this.experience = experience;
        this.experienceToNextLevel = experienceToNextLevel;
        this.gold = gold;
        this.diamond = diamond;
        this.maxDmg = maxDmg;
        this.minDmg = minDmg;
        this.maxHp = maxHp;
        this.hp = hp;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
        this.freeSkillPoints = freeSkillPoints;
        this.npcKills = npcKills;
        this.pvpWins = pvpWins;
        this.pvpLosses = pvpLosses;
    }

    public void setStatsForClass(Class aClass) {
        final String MAGE = "mage", WARRIOR = "warrior", ARCHER = "archer";
        int minDmg = 0, maxDmg = 0, maxHp = 0;

        switch (aClass.getName()) {
            case MAGE:
                minDmg = aClass.getIntelligence() * 4;
                maxDmg = aClass.getIntelligence() * 7;
                maxHp = aClass.getVitality() * 10;
                break;
            case WARRIOR:
                minDmg = aClass.getStrength() * 3;
                maxDmg = aClass.getStrength() * 5;
                maxHp = aClass.getVitality() * 20;
                break;
            case ARCHER:
                minDmg = aClass.getDexterity() * 4;
                maxDmg = aClass.getDexterity() * 6;
                maxHp = aClass.getVitality() * 15;
                break;
        }
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.hp = maxHp;
        this.maxHp = maxHp;
    }

    public void swapItemToEquipmentOrInventory(Item item, boolean toEquip) {
        int multiplierToEquipOrUnequip = toEquip ? 1 : -1;

        this.strength += item.getStrength() * multiplierToEquipOrUnequip;
        this.dexterity += item.getDexterity() * multiplierToEquipOrUnequip;
        this.intelligence += item.getIntelligence() * multiplierToEquipOrUnequip;
        this.vitality += item.getVitality() * multiplierToEquipOrUnequip;
        this.luck += item.getLuck() * multiplierToEquipOrUnequip;
        reloadAddStatsForSwapItems(item, multiplierToEquipOrUnequip);
    }

    public void reloadAddStatsForSwapItems(Item item, int multiplierToEquipOrUnequip) {
        final String MAGE = "mage", WARRIOR = "warrior", ARCHER = "archer";

        switch (this.getAClass().getName()) {
            case MAGE:
                this.minDmg += (item.getIntelligence() * 4) * multiplierToEquipOrUnequip;
                this.maxDmg += (item.getIntelligence() * 7) * multiplierToEquipOrUnequip;
                this.hp += (item.getVitality() * 10) * multiplierToEquipOrUnequip;
                this.maxHp += (item.getVitality() * 10) * multiplierToEquipOrUnequip;
                break;
            case WARRIOR:
                this.minDmg += (item.getStrength() * 3) * multiplierToEquipOrUnequip;
                this.maxDmg += (item.getStrength() * 5) * multiplierToEquipOrUnequip;
                this.hp += (item.getVitality() * 20) * multiplierToEquipOrUnequip;
                this.maxHp += (item.getVitality() * 20) * multiplierToEquipOrUnequip;
                break;
            case ARCHER:
                this.minDmg += (item.getDexterity() * 4) * multiplierToEquipOrUnequip;
                this.maxDmg += (item.getDexterity() * 6) * multiplierToEquipOrUnequip;
                this.hp += (item.getVitality() * 15) * multiplierToEquipOrUnequip;
                this.maxHp += (item.getVitality() * 15) * multiplierToEquipOrUnequip;
                break;
        }
    }

    public void addFreeSkillPoints(FreeSkillPointDTO freeSkillPointDTO) {
        switch (freeSkillPointDTO.getSkillPointName()) {
            case "strength":
                this.setFreeSkillPoints(this.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                this.setStrength(this.getStrength() + freeSkillPointDTO.getAmount());
                if (Objects.equals(this.getAClass().getName(), "warrior")) {
                    this.setMinDmg(this.getStrength() * 3);
                    this.setMaxDmg(this.getStrength() * 5);
                }
                break;

            case "dexterity":
                this.setFreeSkillPoints(this.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                this.setDexterity(this.getDexterity() + freeSkillPointDTO.getAmount());
                if (Objects.equals(this.getAClass().getName(), "archer")) {
                    this.setMinDmg(this.getDexterity() * 4);
                    this.setMaxDmg(this.getDexterity() * 6);
                }
                break;

            case "intelligence":
                this.setFreeSkillPoints(this.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                this.setIntelligence(this.getIntelligence() + freeSkillPointDTO.getAmount());
                if (Objects.equals(this.getAClass().getName(), "mage")) {
                    this.setMinDmg(this.getIntelligence() * 4);
                    this.setMaxDmg(this.getIntelligence() * 7);
                }
                break;

            case "vitality":
                this.setFreeSkillPoints(this.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                this.setVitality(this.getVitality() + freeSkillPointDTO.getAmount());

                int classMultiplier = 1;

                if (Objects.equals(this.getAClass().getName(), "mage")) classMultiplier = 10;
                else if (Objects.equals(this.getAClass().getName(), "warrior")) classMultiplier = 20;
                else if (Objects.equals(this.getAClass().getName(), "archer")) classMultiplier = 15;

                this.setHp(this.getHp() + freeSkillPointDTO.getAmount() * classMultiplier);
                this.setMaxHp(this.getVitality() * classMultiplier);
                break;

            case "luck":
                this.setFreeSkillPoints(this.getFreeSkillPoints() - freeSkillPointDTO.getAmount());
                this.setLuck(this.getLuck() + freeSkillPointDTO.getAmount());
                break;

            default:
                break;
        }
    }
}
