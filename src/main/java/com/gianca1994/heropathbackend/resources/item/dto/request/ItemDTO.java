package com.gianca1994.heropathbackend.resources.item.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for the Item entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private String name;
    private String type;
    private int lvlMin;
    private String classRequired;
    private int price;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;
    private int luck;
    private boolean shop;
}
