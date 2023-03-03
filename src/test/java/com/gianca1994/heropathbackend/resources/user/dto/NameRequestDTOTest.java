package com.gianca1994.heropathbackend.resources.user.dto;

import com.gianca1994.heropathbackend.resources.user.dto.request.NameRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameRequestDTOTest {

    NameRequestDTO nameRequestDTO;

    @BeforeEach
    void setUp() {
        nameRequestDTO = new NameRequestDTO();
        nameRequestDTO.setName("test");
    }

    @Test
    void constructorAllArgs(){
        NameRequestDTO nameRequestDTO = new NameRequestDTO("test");
        assertEquals("test", nameRequestDTO.getName());
    }

    @Test
    void constructorNotArgs(){
        NameRequestDTO nameRequestDTO = new NameRequestDTO();
        assertNull(nameRequestDTO.getName());
    }

    @Test
    void givenNameRequestDTO_whenGetName_thenReturnName() {
        assertEquals("test", nameRequestDTO.getName());
    }
}