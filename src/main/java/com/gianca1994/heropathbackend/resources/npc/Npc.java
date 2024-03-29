package com.gianca1994.heropathbackend.resources.npc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * @Author: Gianca1994
 * @Explanation: This class is the entity of the table npcs in the database.
 */

@Entity
@Table(name = "npcs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Npc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private short level;

    @Column(nullable = false)
    @JsonIgnore
    private int giveMinExp;

    @Column(nullable = false)
    @JsonIgnore
    private int giveMaxExp;

    @Column(nullable = false)
    @JsonIgnore
    private long giveMinGold;

    @Column(nullable = false)
    @JsonIgnore
    private long giveMaxGold;

    @Column(nullable = false)
    private int hp;

    @Column(nullable = false)
    private int maxHp;

    @Column(nullable = false)
    private int minDmg;

    @Column(nullable = false)
    private int maxDmg;

    @Column(nullable = false)
    private int defense;

    @Column(nullable = false)
    private String zone;

    public Npc(String name, short level, int giveMinExp, int giveMaxExp, Long giveMinGold, Long giveMaxGold, int hp, int maxHp, int minDmg, int maxDmg, int defense, String zone) {
        this.name = name;
        this.level = level;
        this.giveMinExp = giveMinExp;
        this.giveMaxExp = giveMaxExp;
        this.giveMinGold = giveMinGold;
        this.giveMaxGold = giveMaxGold;
        this.hp = hp;
        this.maxHp = maxHp;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.defense = defense;
        this.zone = zone;
    }
}
