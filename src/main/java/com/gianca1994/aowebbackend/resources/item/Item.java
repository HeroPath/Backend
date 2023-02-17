package com.gianca1994.aowebbackend.resources.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * @Author: Gianca1994
 * Explanation: This class is the model of the item table in the database.
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int lvlMin;

    @Column(nullable = false)
    private int price;

    @Column()
    private String classRequired;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Item(String name, String type, int lvlMin, int price, String classRequired, int strength, int dexterity, int intelligence, int vitality, int luck) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.classRequired = classRequired;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
    }

    public Item(String name, String type, int lvlMin, int price, String classRequired, int strength, int dexterity, int intelligence, int vitality, int luck, User user) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.classRequired = classRequired;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
        this.user = user;
    }
}
