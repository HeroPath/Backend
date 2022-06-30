package com.gianca1994.aowebbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
}
