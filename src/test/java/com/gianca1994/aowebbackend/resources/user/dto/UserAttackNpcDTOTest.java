package com.gianca1994.aowebbackend.resources.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAttackNpcDTOTest {

    UserAttackNpcDTO userAttackNpcDTO;

    @BeforeEach
    void setUp() {
        userAttackNpcDTO = new UserAttackNpcDTO();
        userAttackNpcDTO.setName("test");
    }

    @Test
    void constructorAllArgs(){
        UserAttackNpcDTO userAttackNpcDTO = new UserAttackNpcDTO("test");
        assertEquals("test", userAttackNpcDTO.getName());
    }

    @Test
    void constructorNotArgs(){
        UserAttackNpcDTO userAttackNpcDTO = new UserAttackNpcDTO();
        assertNull(userAttackNpcDTO.getName());
    }

    @Test
    void givenUserAttackNpcDTO_whenGetName_thenReturnName() {
        assertEquals("test", userAttackNpcDTO.getName());
    }
}