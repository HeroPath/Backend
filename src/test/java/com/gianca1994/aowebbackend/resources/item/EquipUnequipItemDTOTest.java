package com.gianca1994.aowebbackend.resources.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EquipUnequipItemDTOTest {

    private EquipUnequipItemDTO equipUnequipItemDTO;

    @BeforeEach
    void setUp() {
        equipUnequipItemDTO = new EquipUnequipItemDTO();
    }

    @Test
    void constructorNotArgsTest() {
        EquipUnequipItemDTO equipUnequipItemDTO = new EquipUnequipItemDTO();
        assertNotNull(equipUnequipItemDTO);
    }

    @Test
    void constructorAllArgsTest() {
        EquipUnequipItemDTO equipUnequipItemDTO = new EquipUnequipItemDTO(1L);
        assertNotNull(equipUnequipItemDTO);
    }

    @Test
    void getId() {
        equipUnequipItemDTO.setId(1L);
        assertEquals(1L, equipUnequipItemDTO.getId());
    }

    @Test
    void setId() {
        equipUnequipItemDTO.setId(1L);
        assertEquals(1L, equipUnequipItemDTO.getId());
    }
}