package com.gianca1994.aowebbackend.resources.title;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: Gianca1994
 * Explanation: This class is the Title entity, it is used to store the titles of the users
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Title {
    private String name;
    private int minLvl;
    private int minPts;

    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;
    private int luck;
}
