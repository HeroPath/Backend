package com.gianca1994.aowebbackend.combatSystem.pve;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gianca1994.aowebbackend.resources.npc.Npc;
import com.gianca1994.aowebbackend.resources.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PveModelTest {

    private PveModel pveModelUnderTest;

    private final User user = new User();
    private final Npc npc = new Npc();
    private final ArrayList<ObjectNode> historyCombat = new ArrayList<>();

    @BeforeEach
    void setUp() {
        pveModelUnderTest = new PveModel();
        pveModelUnderTest.setHistoryCombat(historyCombat);
        pveModelUnderTest.setUser(user);
        pveModelUnderTest.setNpc(npc);
    }

    @Test
    public void constructorNotArgsTest() {
        pveModelUnderTest = new PveModel();
        assertThat(pveModelUnderTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        pveModelUnderTest = new PveModel(historyCombat, user, npc);
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