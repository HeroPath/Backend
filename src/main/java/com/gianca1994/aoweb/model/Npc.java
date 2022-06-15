package com.gianca1994.aoweb.model;

import javax.persistence.*;

@Entity
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
    private int minHp;

    @Column(nullable = false)
    private int maxHp;

    @Column(nullable = false)
    private int minHit;

    @Column(nullable = false)
    private int maxHit;

    @Column(nullable = false)
    private int defense;
}
