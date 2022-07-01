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

    @Column(nullable = false)
    private int price;

    @Column()
    private int amount;

    @Column()
    private int minDmg;

    @Column()
    private int maxDmg;

    @Column()
    private int minDef;

    @Column()
    private int maxDef;


    public Item(String name, String type, short lvlMin, int price, int minDmg, int maxDmg, int minDef, int maxDef) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.minDef = minDef;
        this.maxDef = maxDef;
    }

    public Item(String name, String type, short lvlMin, int price, int amount, int minDmg, int maxDmg, int minDef, int maxDef) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.amount = amount;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.minDef = minDef;
        this.maxDef = maxDef;
    }
}
