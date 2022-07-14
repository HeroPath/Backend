package com.gianca1994.aowebbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "titles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int minLvl;

    @Column(nullable = false)
    private int minPts;

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

    public Title(String name, int minLvl, int minPts, int strength, int dexterity, int intelligence, int vitality, int luck) {
        this.name = name;
        this.minLvl = minLvl;
        this.minPts = minPts;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
    }
}
