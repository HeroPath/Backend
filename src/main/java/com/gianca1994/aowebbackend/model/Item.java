package com.gianca1994.aowebbackend.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author: Gianca1994
 * Explanation: Class
 */

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private short lvlMin;

    @Column()
    private String classRequired;

    @Column(nullable = false)
    private int price;

    @Column()
    private int amount;

    @Column()
    private int strength;

    @Column()
    private int dexterity;

    @Column()
    private int intelligence;

    @Column()
    private int vitality;

    @Column()
    private int luck;


    public Item(String name, String type, short lvlMin, int price, int strength, int dexterity, int intelligence, int vitality, int luck) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
    }

    public Item(String name, String type, short lvlMin, String classRequired, int price, int amount, int strength, int dexterity, int intelligence, int vitality, int luck) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.classRequired = classRequired;
        this.price = price;
        this.amount = amount;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
    }

}
