package com.gianca1994.aowebbackend.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private Long giveMinExp;

    @Column(nullable = false)
    private Long giveMaxExp;

    @Column(nullable = false)
    private Long giveMinGold;

    @Column(nullable = false)
    private Long giveMaxGold;

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

    public Npc(String name, Long giveMinExp, Long giveMaxExp, Long giveMinGold, Long giveMaxGold, int hp, int maxHp, int minDmg, int maxDmg, int defense) {
        this.name = name;
        this.giveMinExp = giveMinExp;
        this.giveMaxExp = giveMaxExp;
        this.giveMinGold = giveMinGold;
        this.giveMaxGold = giveMaxGold;
        this.hp = hp;
        this.maxHp = maxHp;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.defense = defense;
    }
}
