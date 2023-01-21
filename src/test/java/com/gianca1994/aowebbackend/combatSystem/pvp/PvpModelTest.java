package com.gianca1994.aowebbackend.combatSystem.pvp;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.combatSystem.pve.PveModel;
import com.gianca1994.aowebbackend.resources.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PvpModelTest {

    private PvpModel pvpModelUnderTest;

    private final User attacker = new User();
    private final User defender = new User();
    private final ArrayList<ObjectNode> historyCombat = new ArrayList<>();

    @BeforeEach
    void setUp() {
        pvpModelUnderTest = new PvpModel();
        pvpModelUnderTest.setAttacker(attacker);
        pvpModelUnderTest.setDefender(defender);
        pvpModelUnderTest.setHistoryCombat(historyCombat);
    }

    @Test
    public void constructorNotArgsTest() {
        pvpModelUnderTest = new PvpModel();
        assertThat(pvpModelUnderTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        pvpModelUnderTest = new PvpModel(attacker, defender, historyCombat);
        assertThat(pvpModelUnderTest).isNotNull();
    }

    @Test
    void getAttacker() {
        assertThat(pvpModelUnderTest.getAttacker()).isEqualTo(attacker);
    }

    @Test
    void getDefender() {
        assertThat(pvpModelUnderTest.getDefender()).isEqualTo(defender);
    }

    @Test
    void getHistoryCombat() {
        assertThat(pvpModelUnderTest.getHistoryCombat()).isEqualTo(historyCombat);
    }

    @Test
    void setAttacker() {
        final User attacker2 = new User();
        pvpModelUnderTest.setAttacker(attacker2);
        assertThat(pvpModelUnderTest.getAttacker()).isEqualTo(attacker2);
    }

    @Test
    void setDefender() {
        final User defender2 = new User();
        pvpModelUnderTest.setDefender(defender2);
        assertThat(pvpModelUnderTest.getDefender()).isEqualTo(defender2);
    }

    @Test
    void setHistoryCombat() {
        final ArrayList<ObjectNode> historyCombat2 = new ArrayList<>();
        pvpModelUnderTest.setHistoryCombat(historyCombat2);
        assertThat(pvpModelUnderTest.getHistoryCombat()).isEqualTo(historyCombat2);
    }
}