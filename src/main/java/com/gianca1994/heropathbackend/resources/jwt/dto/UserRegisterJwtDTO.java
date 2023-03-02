package com.gianca1994.heropathbackend.resources.jwt.dto;

import com.gianca1994.heropathbackend.config.ModifConfig;
import com.gianca1994.heropathbackend.resources.classes.Class;
import com.gianca1994.heropathbackend.resources.equipment.Equipment;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to register the user.
 */

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
}
