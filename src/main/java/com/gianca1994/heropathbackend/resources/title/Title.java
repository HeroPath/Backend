package com.gianca1994.heropathbackend.resources.title;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to create a title object
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
