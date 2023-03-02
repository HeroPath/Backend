package com.gianca1994.heropathbackend.resources.guild;

import com.gianca1994.heropathbackend.resources.guild.dto.request.RequestGuildNameDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestGuildNameDTOTest {

    RequestGuildNameDTO requestGuildNameDTO;

    @BeforeEach
    void setUp() {
        requestGuildNameDTO = new RequestGuildNameDTO();
        requestGuildNameDTO.setName("test");
    }

    @Test
    void constructorAllArgs() {
        RequestGuildNameDTO requestGuildNameDTO = new RequestGuildNameDTO("test");
        assertEquals("test", requestGuildNameDTO.getName());
    }

    @Test
    void constructorNoArgs() {
        RequestGuildNameDTO requestGuildNameDTO = new RequestGuildNameDTO();
        assertNull(requestGuildNameDTO.getName());
    }

    @Test
    void givenRequestGuildNameDTO_whenGetName_thenReturnName() {
        assertEquals("test", requestGuildNameDTO.getName());
    }
}