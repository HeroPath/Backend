package com.gianca1994.heropathbackend.resources.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.heropathbackend.utils.ItemQuality;
import com.gianca1994.heropathbackend.resources.item.utilities.ItemConst;
import com.gianca1994.heropathbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * @Author: Gianca1994
 * @Explanation: This class is the model of the item table in the database.
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
    private String quality;

    @Column()
    private int itemLevel;

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

    @Column(nullable = false)
    @JsonIgnore
    private boolean shop;

    @Column(columnDefinition = "boolean default false")
    @JsonIgnore
    private boolean inMarket;

    public Item(String name, String type, int lvlMin, int price, String classRequired, int strength,
                int dexterity, int intelligence, int vitality, int luck, boolean shop) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.classRequired = classRequired;
        this.quality = !ItemConst.ITEM_NOT_LEVEL_AND_QUALITY.contains(type) ? ItemQuality.getQuality(1) : "";
        this.itemLevel = 0;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
        this.shop = shop;
    }

    public Item(String name, String type, int lvlMin, int price, String classRequired, String quality, int itemLevel,
                int strength, int dexterity, int intelligence, int vitality, int luck, boolean shop, User user) {
        this.name = name;
        this.type = type;
        this.lvlMin = lvlMin;
        this.price = price;
        this.classRequired = classRequired;
        this.quality = quality;
        this.itemLevel = itemLevel;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.vitality = vitality;
        this.luck = luck;
        this.shop = shop;
        this.user = user;
    }

    public void itemUpgrade() {
        this.itemLevel++;
        this.strength += itemLevel;
        this.dexterity += itemLevel;
        this.intelligence += itemLevel;
        this.vitality += itemLevel;
        this.luck += itemLevel;
    }
}
