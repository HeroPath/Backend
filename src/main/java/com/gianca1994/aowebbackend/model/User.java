package com.gianca1994.aowebbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.repository.TitleRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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
    @JoinTable(name = "user_title",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "title_id",
                    referencedColumnName = "id"))
    private Title title;

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
    private int defense;
    @Column
    private int evasion;
    @Column
    private float criticalChance;
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
    @Column
    private int titlePoints;

    public User(String username, String password, String email, Role role, Class aClass, Title title, Inventory inventory, Equipment equipment, short level, long experience, long experienceToNextLevel, long gold, int diamond, int maxDmg, int minDmg, int maxHp, int hp, int strength, int dexterity, int intelligence, int vitality, int luck, int freeSkillPoints, int npcKills, int pvpWins, int pvpLosses, int titlePoints) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.aClass = aClass;
        this.title = title;
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
        this.titlePoints = titlePoints;
    }

    public void calculateStats(boolean fullMinHp) {
        /**
         * @Author: Gianca1994
         * Explanation: Calculate status method
         * @param boolean fullMinHp
         * @return void
         */
        final String MAGE = "mage", WARRIOR = "warrior", ARCHER = "archer";
        switch (this.getAClass().getName()) {
            case MAGE:
                this.minDmg = this.intelligence * 5;
                this.maxDmg = this.intelligence * 7;
                this.maxHp = this.vitality * 10;
                this.defense = this.strength * 2;
                this.evasion = this.dexterity * 2;
                this.criticalChance = this.luck * 0.15f;
                break;
            case WARRIOR:
                this.minDmg = this.strength * 3;
                this.maxDmg = this.strength * 5;
                this.maxHp = this.vitality * 20;
                this.defense = this.intelligence * 5;
                this.evasion = this.dexterity * 2;
                this.criticalChance = this.luck * 0.1f;
                break;
            case ARCHER:
                this.minDmg = this.dexterity * 4;
                this.maxDmg = this.dexterity * 6;
                this.maxHp = this.vitality * 15;
                this.defense = this.intelligence * 3;
                this.evasion = this.strength * 4;
                this.criticalChance = this.luck * 0.125f;
                break;
        }
        if (fullMinHp) this.hp = this.maxHp;
    }

    public void swapItemToEquipmentOrInventory(Item item, boolean toEquip) {
        /**
         * @Author: Gianca1994
         * Explanation:
         * @param Item item
         * @param boolean toEquip
         * @return none
         */
        int multiplierToEquipOrUnequip = toEquip ? 1 : -1;

        this.strength += item.getStrength() * multiplierToEquipOrUnequip;
        this.dexterity += item.getDexterity() * multiplierToEquipOrUnequip;
        this.intelligence += item.getIntelligence() * multiplierToEquipOrUnequip;
        this.vitality += item.getVitality() * multiplierToEquipOrUnequip;
        this.luck += item.getLuck() * multiplierToEquipOrUnequip;

        calculateStats(false);
    }

    public void addFreeSkillPoints(FreeSkillPointDTO freeSkillPointDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add free skill points to the user.
         * @param freeSkillPointDTO freeSkillPointDTO
         * @return none
         */
        boolean isAdded = true;
        String skillName = freeSkillPointDTO.getSkillPointName().toLowerCase();

        if (skillName.equals("strength")) this.strength += freeSkillPointDTO.getAmount();
        else if (skillName.equals("dexterity")) this.dexterity += freeSkillPointDTO.getAmount();
        else if (skillName.equals("intelligence")) this.intelligence += freeSkillPointDTO.getAmount();
        else if (skillName.equals("vitality")) this.vitality += freeSkillPointDTO.getAmount();
        else if (skillName.equals("luck")) this.luck += freeSkillPointDTO.getAmount();
        else isAdded = false;

        if (isAdded) this.freeSkillPoints -= freeSkillPointDTO.getAmount();
        calculateStats(false);
    }

    public void addTitlePoints(int amount) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add title points to the user.
         * @param int amount
         * @return none
         */
        this.titlePoints += amount;
    }

    public void removeTitlePoints(int amount) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to remove title points from the user.
         * @param int amount
         * @return none
         */
        if (this.titlePoints >= amount) this.titlePoints -= amount;
        else this.titlePoints = 0;
    }

    public void checkStatusTitlePoints(TitleRepository titleRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to check the status of the title points.
         * @param TitleRepository titleRepository
         * @return none
         */
        for (long i = 1; i < 8; i++) {
            Title title = titleRepository.findById(i).get();
            if (this.titlePoints >= title.getMinPts() && this.level >= title.getMinLvl()) {
                this.title = title;
            }
        }
    }
}
