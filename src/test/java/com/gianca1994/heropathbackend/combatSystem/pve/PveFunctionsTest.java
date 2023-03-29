package com.gianca1994.heropathbackend.combatSystem.pve;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.jwt.dto.UserRegisterJwtDTO;
import com.gianca1994.heropathbackend.resources.npc.Npc;
import com.gianca1994.heropathbackend.resources.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PveFunctionsTest {

    private Npc npcTest = new Npc(
            "test", (short) 1,
            10,
            10,
            10L,
            10L,
            100,
            100,
            10,
            10,
            1,
            "test"
    );
    private User userTest = new User(new UserRegisterJwtDTO(
            "testusername", "testpassword", "testusername@test.com", "mage"
    ));

    private final PveFunctions pveFunctionsTest = new PveFunctions();


    @Test
    void givenNpcAndUserDefense_whenCalculateNpcDmg_thenReturnNpcDmg() {
        assertThat(pveFunctionsTest.calculateNpcDmg(npcTest, 0)).isEqualTo(10);
        assertThat(pveFunctionsTest.calculateNpcDmg(npcTest, 10)).isEqualTo(0);
    }

    @Test
    void givenNPC_whenCheckIfNpcDied_thenReturnNpcDied() {
        assertThat(pveFunctionsTest.checkIfNpcDied(npcTest.getHp())).isEqualTo(false);
        npcTest.setHp(0);
        assertThat(pveFunctionsTest.checkIfNpcDied(npcTest.getHp())).isEqualTo(true);
    }

    @Test
    void givenUserAndNpc_whenCheckUserAndNpcAlive_thenReturnUserAndNpcAlive() {
        userTest.setHp(1);
        assertThat(pveFunctionsTest.checkUserAndNpcAlive(true, true)).isEqualTo(true);
        userTest.setHp(0);
        assertThat(pveFunctionsTest.checkUserAndNpcAlive(false, true)).isEqualTo(false);
        npcTest.setHp(0);
        assertThat(pveFunctionsTest.checkUserAndNpcAlive(false, false)).isEqualTo(false);
    }

    @Test
    void givenNpc_whenAmountOfDiamondsDrop_thenReturnAmountOfDiamondsDrop() {
        assertThat(pveFunctionsTest.amountDiamondsDrop(userTest)).isBetween(1, SvConfig.MAX_DIAMOND_DROP);
    }
}