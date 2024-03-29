package com.gianca1994.heropathbackend.resources.user.dto;

import com.gianca1994.heropathbackend.resources.user.dto.request.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    UserRegisterDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserRegisterDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("test");
        userDTO.setEmail("test");
        userDTO.setClassName("test");
    }

    @Test
    void constructorAllArgs(){
        UserRegisterDTO userDTO = new UserRegisterDTO("test", "test", "test", "test");
        assertEquals("test", userDTO.getUsername());
        assertEquals("test", userDTO.getPassword());
        assertEquals("test", userDTO.getEmail());
        assertEquals("test", userDTO.getClassName());
    }

    @Test
    void constructorNotArgs(){
        UserRegisterDTO userDTO = new UserRegisterDTO();
        assertNull(userDTO.getUsername());
        assertNull(userDTO.getPassword());
        assertNull(userDTO.getEmail());
        assertNull(userDTO.getClassName());
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
        assertEquals("test", userDTO.getClassName());
    }
}