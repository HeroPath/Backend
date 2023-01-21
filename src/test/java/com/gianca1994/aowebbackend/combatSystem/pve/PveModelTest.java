package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.resources.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PveModelTest {

    private PveModel pveModelUnderTest;

    private final User user = new User();
    private final ArrayList<ObjectNode> historyCombat = new ArrayList<>();

    @BeforeEach
    void setUp() {
        pveModelUnderTest = new PveModel();
        pveModelUnderTest.setUser(user);
        pveModelUnderTest.setHistoryCombat(historyCombat);
    }

    @Test
    public void constructorNotArgsTest() {
        pveModelUnderTest = new PveModel();
        assertThat(pveModelUnderTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        pveModelUnderTest = new PveModel(user, historyCombat);
        assertThat(pveModelUnderTest).isNotNull();
    }

    @Test
    void getUser() {
        assertThat(pveModelUnderTest.getUser()).isEqualTo(user);
    }

    @Test
    void getHistoryCombat() {
        assertThat(pveModelUnderTest.getHistoryCombat()).isEqualTo(historyCombat);
    }

    @Test
    void setUser() {
        pveModelUnderTest.setUser(user);
        assertThat(pveModelUnderTest.getUser()).isEqualTo(user);
    }

    @Test
    void setHistoryCombat() {
        pveModelUnderTest.setHistoryCombat(historyCombat);
        assertThat(pveModelUnderTest.getHistoryCombat()).isEqualTo(historyCombat);
    }
}