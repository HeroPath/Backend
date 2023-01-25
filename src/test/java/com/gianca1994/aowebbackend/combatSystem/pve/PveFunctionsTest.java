package com.gianca1994.aowebbackend.combatSystem.pve;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.user.User;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PveFunctionsTest {

    private Npc npcTest = new Npc(
            "test", (short) 1,
            10L,
            10L,
            10L,
            10L,
            100,
            100,
            10,
            10,
            1,
            "test"
    );
    private User userTest = new User(
            "testusername", "testpassword", "testusername@test.com", new Role(),
            new Class(),
            new Title(),
            new Inventory(),
            new Equipment(),
            1, 1, 1, 1, 1
    );

    private final PveFunctions pveFunctionsTest = new PveFunctions();


    @Test
    void givenNpcAndUserDefense_whenCalculateNpcDmg_thenReturnNpcDmg() {
        assertThat(pveFunctionsTest.calculateNpcDmg(npcTest, 0)).isEqualTo(10);
        assertThat(pveFunctionsTest.calculateNpcDmg(npcTest, 10)).isEqualTo(0);
    }

    @Test
    void givenNpc_whenCalculateUserExperienceGain_thenReturnUserExperienceGain() {
        assertThat(pveFunctionsTest.CalculateUserExperienceGain(npcTest)).isEqualTo(10L * SvConfig.EXPERIENCE_MULTIPLIER);
    }

    @Test
    void givenNPC_whenCheckIfNpcDied_thenReturnNpcDied() {
        assertThat(pveFunctionsTest.checkIfNpcDied(npcTest)).isEqualTo(false);
        npcTest.setHp(0);
        assertThat(pveFunctionsTest.checkIfNpcDied(npcTest)).isEqualTo(true);
    }

    @Test
    void givenUser_whenCalculateUserGoldGain_thenReturnUserGoldGain() {
        assertThat(pveFunctionsTest.calculateUserGoldGain(npcTest)).isEqualTo(10L * SvConfig.GOLD_MULTIPLIER);
    }

    @Test
    void givenUserAndNpc_whenCheckUserAndNpcAlive_thenReturnUserAndNpcAlive() {
        userTest.setHp(1);
        assertThat(pveFunctionsTest.checkUserAndNpcAlive(userTest, npcTest)).isEqualTo(true);
        userTest.setHp(0);
        assertThat(pveFunctionsTest.checkUserAndNpcAlive(userTest, npcTest)).isEqualTo(false);
        npcTest.setHp(0);
        assertThat(pveFunctionsTest.checkUserAndNpcAlive(userTest, npcTest)).isEqualTo(false);
    }

    @Test
    void givenNpc_whenAmountOfDiamondsDrop_thenReturnAmountOfDiamondsDrop() {
        assertThat(pveFunctionsTest.amountOfDiamondsDrop()).isBetween(1, SvConfig.MAXIMUM_AMOUNT_DIAMONDS_DROP);
    }
}