package com.gianca1994.heropathbackend.combatSystem;

import com.gianca1994.heropathbackend.resources.jwt.dto.UserRegisterJwtDTO;
import com.gianca1994.heropathbackend.resources.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericFunctionsTest {
    private final User userTest = new User(new UserRegisterJwtDTO(
            "testusername", "testpassword", "testemail", "mage"
    ));

    private final GenericFunctions genericFunctionsTest = new GenericFunctions();

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
        int hp = genericFunctionsTest.reduceUserHp(userTest, 100, 10);
        assertEquals(90, hp);
    }

    @Test
    void givenUser_whenCheckIfUserDied_thenReturnTrue() {
        userTest.setHp(0);
        assertTrue(genericFunctionsTest.checkIfUserDied(userTest.getHp()));
    }

    @Test
    void givenUser_whenCheckIfUserDied_thenReturnFalse() {
        userTest.setHp(100);
        assertFalse(genericFunctionsTest.checkIfUserDied(userTest.getHp()));
    }
}