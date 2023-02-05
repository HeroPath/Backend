package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.resources.item.dto.request.EquipUnequipItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipUnequipItemDTOTest {

    private EquipUnequipItemDTO equipUnequipItemDTO;

    @BeforeEach
    void setUp() {
        equipUnequipItemDTO = new EquipUnequipItemDTO();
        equipUnequipItemDTO.setId(1L);
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
    void givenEquipUnequipItemDTO_whenGetId_thenReturnId() {
        assertEquals(1L, equipUnequipItemDTO.getId());
    }
}