package com.gianca1994.aowebbackend.resources.equipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class EquipmentTest {

    private Equipment equipmentTest;

    @BeforeEach
    void setUp() {
        equipmentTest = new Equipment();
        equipmentTest.setId(1L);
        equipmentTest.setItems(new HashSet<>());
    }

    @Test
    public void constructorNotArgsTest() {
        Equipment equipmentTest = new Equipment();
        assertThat(equipmentTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Equipment equipmentTest2 = new Equipment(2L, new HashSet<>());
        assertThat(equipmentTest2).isNotNull();
    }

    @Test
    public void givenEquipment_whenGetId_thenReturnId() {
        assertThat(equipmentTest.getId()).isEqualTo(1L);
    }

    @Test
    public void givenEquipment_whenGetItems_thenReturnItems() {
        assertThat(equipmentTest.getItems()).isEqualTo(new HashSet<>());
    }

    @Test
    public void givenEquipment_whenSetId_thenSetId() {
        equipmentTest.setId(2L);
        assertThat(equipmentTest.getId()).isEqualTo(2L);
    }

    @Test
    public void givenEquipment_whenSetItems_thenSetItems() {
        equipmentTest.setItems(new HashSet<>());
        assertThat(equipmentTest.getItems()).isEqualTo(new HashSet<>());
    }

    @Test
    public void givenEquipment_whenRemoveItem_thenRemoveItem() {
        equipmentTest.setItems(new HashSet<>());
        assertThat(equipmentTest.getItems().size()).isEqualTo(0);
    }

    @Test
    public void givenEquipment_whenEquals_thenReturnFalse() {
        Equipment equipmentTest2 = new Equipment(1L, new HashSet<>());
        assertThat(equipmentTest.equals(equipmentTest2)).isFalse();
    }

}