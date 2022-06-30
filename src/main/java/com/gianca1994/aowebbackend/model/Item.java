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
    private int minDmg;

    @Column()
    private int maxDmg;

    @Column()
    private int minDef;

    @Column()
    private int maxDef;

}
