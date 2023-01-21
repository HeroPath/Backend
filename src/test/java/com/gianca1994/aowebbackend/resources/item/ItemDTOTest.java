package com.gianca1994.aowebbackend.resources.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemDTOTest {

    private ItemDTO itemDTO;

    @BeforeEach
    void setUp() {
        itemDTO = new ItemDTO();

        itemDTO.setName("Test");
        itemDTO.setType("Test");
        itemDTO.setLvlMin(1);
        itemDTO.setClassRequired("Test");
        itemDTO.setPrice(1);
        itemDTO.setStrength(1);
        itemDTO.setDexterity(1);
        itemDTO.setIntelligence(1);
        itemDTO.setVitality(1);
        itemDTO.setLuck(1);
    }

    @Test
    public void constructorNotArgsTest() {
        ItemDTO item = new ItemDTO();
        assertThat(item).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        ItemDTO item = new ItemDTO("Test", "Test", 1, "Test", 1, 1, 1, 1, 1, 1);
        assertThat(item).isNotNull();
    }

    @Test
    public void givenItemDTO_whenGetName_thenReturnName() {
        assertThat(itemDTO.getName()).isEqualTo("Test");
    }

    @Test
    public void givenItemDTO_whenGetType_thenReturnType() {
        assertThat(itemDTO.getType()).isEqualTo("Test");
    }

    @Test
    public void givenItemDTO_whenGetLvlMin_thenReturnLvlMin() {
        assertThat(itemDTO.getLvlMin()).isEqualTo(1);
    }

    @Test
    public void givenItemDTO_whenGetClassRequired_thenReturnClassRequired() {
        assertThat(itemDTO.getClassRequired()).isEqualTo("Test");
    }

    @Test
    public void givenItemDTO_whenGetPrice_thenReturnPrice() {
        assertThat(itemDTO.getPrice()).isEqualTo(1);
    }

    @Test
    public void givenItemDTO_whenGetStrength_thenReturnStrength() {
        assertThat(itemDTO.getStrength()).isEqualTo(1);
    }

    @Test
    public void givenItemDTO_whenGetDexterity_thenReturnDexterity() {
        assertThat(itemDTO.getDexterity()).isEqualTo(1);
    }

    @Test
    public void givenItemDTO_whenGetIntelligence_thenReturnIntelligence() {
        assertThat(itemDTO.getIntelligence()).isEqualTo(1);
    }

    @Test
    public void givenItemDTO_whenGetVitality_thenReturnVitality() {
        assertThat(itemDTO.getVitality()).isEqualTo(1);
    }

    @Test
    public void givenItemDTO_whenGetLuck_thenReturnLuck() {
        assertThat(itemDTO.getLuck()).isEqualTo(1);
    }

    @Test
    public void givenItem_whenEquals_thenReturnFalse() {
        ItemDTO itemDTO2 = new ItemDTO("Test2", "Test2", 2, "Test2", 2, 2, 2, 2, 2, 2);
        assertThat(itemDTO.equals(itemDTO2)).isFalse();
    }
}