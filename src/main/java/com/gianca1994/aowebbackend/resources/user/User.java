package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.user.dto.FreeSkillPointDTO;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.quest.Quest;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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
public class User extends Account {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_quests",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "quest_id")}
    )
    private Set<Quest> quests = new HashSet<>();

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
    @Column
    private String guildName;

    public User(String username, String password, String email, Role role, Class aClass, Title title, Inventory inventory, Equipment equipment, int strength, int dexterity, int intelligence, int vitality, int luck) {
        super(username, password, email, role);
        this.aClass = aClass;
        this.title = title;
        this.inventory = inventory;
        this.equipment = equipment;
        this.level = ModifConfig.START_LVL;
        this.experience = ModifConfig.START_EXP;
        this.experienceToNextLevel = ModifConfig.START_EXP_TO_NEXT_LVL;
        this.gold = ModifConfig.START_GOLD;
        this.diamond = ModifConfig.START_DIAMOND;
        this.maxDmg = 0;
        this.minDmg = 0;
        this.maxHp = 0;
        this.hp = 0;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
        this.freeSkillPoints = ModifConfig.START_FREE_SKILL_POINTS;
        this.npcKills = 0;
        this.pvpWins = 0;
        this.pvpLosses = 0;
        this.titlePoints = 0;
        this.guildName = "";
    }

    public void calculateStats(boolean fullMinHp) {
        /**
         * @Author: Gianca1994
         * Explanation: Calculate status method
         * @param boolean fullMinHp
         * @return void
         */
        switch (this.getAClass().getName()) {
            case ModifConfig.MAGE_NAME:
                this.minDmg = this.intelligence * ModifConfig.MIN_DMG_MAGE;
                this.maxDmg = this.intelligence * ModifConfig.MAX_DMG_MAGE;
                this.maxHp = this.vitality * ModifConfig.MAX_HP_MAGE;
                this.defense = this.strength * ModifConfig.DEFENSE_MAGE;
                this.evasion = this.dexterity * ModifConfig.EVASION_MAGE;
                this.criticalChance = this.luck * ModifConfig.CRITICAL_MAGE > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : this.luck * ModifConfig.CRITICAL_MAGE;
                break;
            case ModifConfig.WARRIOR_NAME:
                this.minDmg = this.strength * ModifConfig.MIN_DMG_WARRIOR;
                this.maxDmg = this.strength * ModifConfig.MAX_DMG_WARRIOR;
                this.maxHp = this.vitality * ModifConfig.MAX_HP_WARRIOR;
                this.defense = this.intelligence * ModifConfig.DEFENSE_WARRIOR;
                this.evasion = this.dexterity * ModifConfig.EVASION_WARRIOR;
                this.criticalChance = this.luck * ModifConfig.CRITICAL_WARRIOR > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : this.luck * ModifConfig.CRITICAL_WARRIOR;
                break;
            case ModifConfig.ARCHER_NAME:
                this.minDmg = this.dexterity * ModifConfig.MIN_DMG_ARCHER;
                this.maxDmg = this.dexterity * ModifConfig.MAX_DMG_ARCHER;
                this.maxHp = this.vitality * ModifConfig.MAX_HP_ARCHER;
                this.defense = this.intelligence * ModifConfig.DEFENSE_ARCHER;
                this.evasion = this.strength * ModifConfig.EVASION_ARCHER;
                this.criticalChance = this.luck * ModifConfig.CRITICAL_ARCHER > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : this.luck * ModifConfig.CRITICAL_ARCHER;
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
        Title currentTitle = titleRepository.findById(this.title.getId()).get();

        for (Title t : titleRepository.findAll()) {
            if (this.titlePoints >= t.getMinPts() && this.level >= t.getMinLvl()) {
                this.title = t;
            }
        }
        if (!Objects.equals(this.title.getId(), currentTitle.getId())) {
            this.strength += this.title.getStrength() - currentTitle.getStrength();
            this.dexterity += this.title.getDexterity() - currentTitle.getDexterity();
            this.intelligence += this.title.getIntelligence() - currentTitle.getIntelligence();
            this.vitality += this.title.getVitality() - currentTitle.getVitality();
            this.luck += this.title.getLuck() - currentTitle.getLuck();
        }
        calculateStats(false);
    }
}