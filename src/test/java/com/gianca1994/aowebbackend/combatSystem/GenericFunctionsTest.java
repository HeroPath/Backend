package com.gianca1994.aowebbackend.combatSystem;

import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericFunctionsTest {

    private User userTest = new User(
            "testusername", "testpassword", "testusername@test.com",
            new Role(1L, "testRole"),
            new Class(),
            new Title(),
            new Inventory(),
            new Equipment(),
            1, 1, 1, 1, 1
    );

    private GenericFunctions genericFunctionsTest = new GenericFunctions();

    @Test
    void givenUser_whenCheckLifeStartCombat_thenReturnTrue() {
        userTest.setMaxHp(100);
        userTest.setHp(10);
        assertTrue(genericFunctionsTest.checkLifeStartCombat(userTest));
    }

    @Test
    void givenUser_whenCheckLifeStartCombat_thenReturnFalse() {
        userTest.setHp(100);
        assertFalse(genericFunctionsTest.checkLifeStartCombat(userTest));
    }

    @Test
    void givenUserAndDefense_whenGetUserDmg_thenReturnDmg() {
        userTest.setMinDmg(10);
        userTest.setMaxDmg(20);
        userTest.setCriticalChance(0);
        int dmg = genericFunctionsTest.getUserDmg(userTest, 5);
        assertTrue(dmg >= 5 && dmg <= 15);
    }

    @Test
    void givenUserAndDmg_whenUserReceiveDmg_thenReturnHp() {
        userTest.setHp(100);
        int hp = genericFunctionsTest.userReceiveDmg(userTest, 10);
        assertEquals(90, hp);
    }

    @Test
    void givenUser_whenCheckIfUserDied_thenReturnTrue() {
        userTest.setHp(0);
        assertTrue(genericFunctionsTest.checkIfUserDied(userTest));
    }

    @Test
    void givenUser_whenCheckIfUserDied_thenReturnFalse() {
        userTest.setHp(100);
        assertFalse(genericFunctionsTest.checkIfUserDied(userTest));
    }
}