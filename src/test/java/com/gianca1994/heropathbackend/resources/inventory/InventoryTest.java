package com.gianca1994.heropathbackend.resources.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class InventoryTest {

    private Inventory inventoryTest;

    @BeforeEach
    void setUp() {
        inventoryTest = new Inventory();
        inventoryTest.setId(1L);
        inventoryTest.setItems(new HashSet<>());
    }

    @Test
    public void constructorNotArgsTest() {
        Inventory inventoryTest = new Inventory();
        assertThat(inventoryTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Inventory inventoryTest2 = new Inventory(2L, new HashSet<>());
        assertThat(inventoryTest2).isNotNull();
    }

    @Test
    public void givenInventory_whenGetId_thenReturnId() {
        assertThat(inventoryTest.getId()).isEqualTo(1L);
    }

    @Test
    public void givenInventory_whenGetItems_thenReturnItems() {
        assertThat(inventoryTest.getItems()).isEqualTo(new HashSet<>());
    }

    @Test
    public void givenInventory_whenRemoveItem_thenRemoveItem() {
        inventoryTest.setItems(new HashSet<>());
        assertThat(inventoryTest.getItems().size()).isEqualTo(0);
    }

    @Test
    public void givenInventory_whenEquals_thenReturnFalse() {
        Inventory inventoryTest2 = new Inventory(1L, new HashSet<>());
        assertThat(inventoryTest.equals(inventoryTest2)).isFalse();
    }
}