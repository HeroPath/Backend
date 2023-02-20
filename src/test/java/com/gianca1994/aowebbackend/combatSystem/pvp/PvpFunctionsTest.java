package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.jwt.dto.UserRegisterJwtDTO;
import com.gianca1994.aowebbackend.resources.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PvpFunctionsTest {

    private User userTest1 = new User(new UserRegisterJwtDTO(
            "testusername", "testpassword", "testusername@test.com", "mage"
    ));

    private User userTest2 = new User(new UserRegisterJwtDTO(
            "testusername2", "testpassword2", "testusername2@test.com", "mage"
    ));

    private final PvpFunctions pvpFunctionsTest = new PvpFunctions();


    @Test
    void givenUserDefender_whenCalculateUserAttackerGoldAmountLose_thenReturnUserAttackerGoldAmountLose() {
        userTest2.setGold(100);
        assertThat(pvpFunctionsTest.getUserGoldAmountLose(userTest2)).isEqualTo(75);
        userTest2.setGold(0);
        assertThat(pvpFunctionsTest.getUserGoldAmountLose(userTest2)).isEqualTo(0);
    }

    @Test
    void givenUserAttacker_whenCalculateUserAttackerGoldLoseForLoseCombat_thenReturnUserAttackerGoldLoseForLoseCombat() {
        userTest1.setGold(100);
        assertThat(pvpFunctionsTest.getUserGoldLoseForLoseCombat(userTest1)).isEqualTo(10);
        userTest1.setGold(0);
        assertThat(pvpFunctionsTest.getUserGoldLoseForLoseCombat(userTest1)).isEqualTo(0);
    }

    @Test
    void givenUserAttackerAndUserDefender_whenCheckBothUsersAlive_thenReturnTrue() {
        userTest1.setHp(1);
        userTest2.setHp(1);
        assertThat(pvpFunctionsTest.checkBothUsersAlive(userTest1, userTest2)).isTrue();
    }

    @Test
    void givenUserAttackerAndUserDefender_whenCheckBothUsersAlive_thenReturnFalse() {
        userTest1.setHp(0);
        userTest2.setHp(1);
        assertThat(pvpFunctionsTest.checkBothUsersAlive(userTest1, userTest2)).isFalse();
        userTest1.setHp(1);
        userTest2.setHp(0);
        assertThat(pvpFunctionsTest.checkBothUsersAlive(userTest1, userTest2)).isFalse();
        userTest1.setHp(0);
        userTest2.setHp(0);
        assertThat(pvpFunctionsTest.checkBothUsersAlive(userTest1, userTest2)).isFalse();
    }

    @Test
    void whenCalculatePointsTitleWin_thenReturnPointsTitleWin() {
        userTest2.setTitlePoints(150);
        assertThat(pvpFunctionsTest.calculatePointsTitleWinOrLose(userTest2)).isBetween(
                SvConfig.PVP_MIN_RATE_POINT_TITLE, SvConfig.PVP_MAX_RATE_POINT_TITLE
        );
    }
}