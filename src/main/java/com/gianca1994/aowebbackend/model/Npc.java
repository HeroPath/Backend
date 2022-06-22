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

    @Column(nullable = false)
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
}
