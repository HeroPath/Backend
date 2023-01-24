package com.gianca1994.aowebbackend.resources.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAttackUserDTOTest {

    UserAttackUserDTO userAttackUserDTO;

    @BeforeEach
    void setUp() {
        userAttackUserDTO = new UserAttackUserDTO();
        userAttackUserDTO.setName("test");
    }

    @Test
    void constructorAllArgs(){
        UserAttackUserDTO userAttackUserDTO = new UserAttackUserDTO("test");
        assertEquals("test", userAttackUserDTO.getName());
    }

    @Test
    void constructorNotArgs(){
        UserAttackUserDTO userAttackUserDTO = new UserAttackUserDTO();
        assertNull(userAttackUserDTO.getName());
    }

    @Test
    void givenUserAttackUserDTO_whenGetName_thenReturnName() {
        assertEquals("test", userAttackUserDTO.getName());
    }
}