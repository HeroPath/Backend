package com.gianca1994.aowebbackend.resources.jwt.dto;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterJwtDTO {
    private String username;
    private String password;
    private String email;
    private Inventory inventory;
    private Equipment equipment;
    private Class aClass;
    private int strength;
    private int dexterity;
    private int intelligence;
    private int vitality;
    private int luck;

    public UserRegisterJwtDTO(String username, String password, String email, String className) {
        this.username = username.toLowerCase();
        this.password = password;
        this.email = email.toLowerCase();
        this.aClass = ModifConfig.CLASSES.stream().filter(c -> c.getName().equalsIgnoreCase(className)).findFirst().orElse(null);
    }

    public void setAClassAttributes() {
        this.strength = this.aClass.getStrength();
        this.dexterity = this.aClass.getDexterity();
        this.intelligence = this.aClass.getIntelligence();
        this.vitality = this.aClass.getVitality();
        this.luck = this.aClass.getLuck();
    }
}
