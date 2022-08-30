package com.gianca1994.aowebbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * @Author: Gianca1994
 * Explanation: Class
 */

@Entity
@Table(name = "quests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private long giveExp;
    @Column
    private long giveGold;

    public Quest(String name, String description, long giveExp, long giveGold) {
        this.name = name;
        this.description = description;
        this.giveExp = giveExp;
        this.giveGold = giveGold;
    }
}
