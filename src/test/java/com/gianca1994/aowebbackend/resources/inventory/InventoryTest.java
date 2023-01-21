package com.gianca1994.aowebbackend.resources.inventory;

import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventoryTest;

    @BeforeEach
    void setUp() {
        inventoryTest = new Inventory();
        inventoryTest.setId(1L);
        inventoryTest.setItems(new ArrayList<>());
    }

    @Test
    public void constructorNotArgsTest() {
        Inventory inventoryTest = new Inventory();
        assertThat(inventoryTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Inventory inventoryTest2 = new Inventory(2L, new ArrayList<>());
        assertThat(inventoryTest2).isNotNull();
    }

    @Test
    public void givenInventory_whenGetId_thenReturnId() {
        assertThat(inventoryTest.getId()).isEqualTo(1L);
    }

    @Test
    public void givenInventory_whenGetItems_thenReturnItems() {
        assertThat(inventoryTest.getItems()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void givenInventory_whenSetId_thenSetId() {
        inventoryTest.setId(2L);
        assertThat(inventoryTest.getId()).isEqualTo(2L);
    }

    @Test
    public void givenInventory_whenSetItems_thenSetItems() {
        inventoryTest.setItems(new ArrayList<>());
        assertThat(inventoryTest.getItems()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void givenInventory_whenRemoveItem_thenRemoveItem() {
        inventoryTest.setItems(new ArrayList<>());
        assertThat(inventoryTest.getItems().size()).isEqualTo(0);
    }

    @Test
    public void givenInventory_whenEquals_thenReturnFalse() {
        Inventory inventoryTest2 = new Inventory(1L, new ArrayList<>());
        assertThat(inventoryTest.equals(inventoryTest2)).isFalse();
    }
}