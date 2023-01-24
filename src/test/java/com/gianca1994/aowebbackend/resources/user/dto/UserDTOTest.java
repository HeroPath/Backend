package com.gianca1994.aowebbackend.resources.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("test");
        userDTO.setEmail("test");
        userDTO.setClassId(1);
    }

    @Test
    void constructorAllArgs(){
        UserDTO userDTO = new UserDTO("test", "test", "test", 1);
        assertEquals("test", userDTO.getUsername());
        assertEquals("test", userDTO.getPassword());
        assertEquals("test", userDTO.getEmail());
        assertEquals(1, userDTO.getClassId());
    }

    @Test
    void constructorNotArgs(){
        UserDTO userDTO = new UserDTO();
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getPassword());
        assertNull(userDTO.getEmail());
        assertEquals(0, userDTO.getClassId());
    }

    @Test
    void givenUserDTO_whenGetUsername_thenReturnUsername() {
        assertEquals("test", userDTO.getUsername());
    }

    @Test
    void givenUserDTO_whenGetPassword_thenReturnPassword() {
        assertEquals("test", userDTO.getPassword());
    }

    @Test
    void givenUserDTO_whenGetEmail_thenReturnEmail() {
        assertEquals("test", userDTO.getEmail());
    }

    @Test
    void givenUserDTO_whenGetClassId_thenReturnClassId() {
        assertEquals(1, userDTO.getClassId());
    }
}