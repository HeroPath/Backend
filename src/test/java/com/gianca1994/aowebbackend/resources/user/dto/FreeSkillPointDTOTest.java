package com.gianca1994.aowebbackend.resources.user.dto;

import com.gianca1994.aowebbackend.resources.user.dto.request.FreeSkillPointDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FreeSkillPointDTOTest {

    FreeSkillPointDTO freeSkillPointDTO;

    @BeforeEach
    void setUp() {
        freeSkillPointDTO = new FreeSkillPointDTO();
        freeSkillPointDTO.setSkillPointName("test");
        freeSkillPointDTO.setAmount(1);
    }

    @Test
    void constructorNotArgs() {
        FreeSkillPointDTO freeSkillPointDTOTest = new FreeSkillPointDTO();
        assertNull(freeSkillPointDTOTest.getSkillPointName());
    }

    @Test
    void givenFreeSkillPointDTO_whenGetSkillPointName_thenReturnSkillPointName() {
        assertEquals("test", freeSkillPointDTO.getSkillPointName());
    }

    @Test
    void givenFreeSkillPointDTO_whenGetAmount_thenReturnAmount() {
        assertEquals(1, freeSkillPointDTO.getAmount());
    }
}