package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.resources.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setUsername("test");
        account.setPassword("test");
        account.setEmail("test@test.com");
        account.setRole(new Role());
    }

    @Test
    void constructorAllArgsTest() {
        Account account = new Account(1L,"test", "test", "test@test.com", new Role());
        assertNotNull(account.getId());
        assertEquals("test", account.getUsername());
        assertEquals("test", account.getPassword());
        assertEquals("test@test.com", account.getEmail());
        assertNotNull(account.getRole());
    }

    @Test
    void constructorNotArgsTest() {
        Account account = new Account();
        assertNull(account.getId());
        assertNull(account.getUsername());
        assertNull(account.getPassword());
        assertNull(account.getEmail());
        assertNull(account.getRole());
    }

    @Test
    void constructorNotAllArgsTest() {
        Account account = new Account("test", "test", "test@test.com", new Role());
        assertEquals("test", account.getUsername());
        assertEquals("test", account.getPassword());
        assertEquals("test@test.com", account.getEmail());
        assertNotNull(account.getRole());
    }

    @Test
    void givenAccount_whenGetId_thenReturnId() {
        assertEquals(1L, account.getId());
    }

    @Test
    void givenAccount_whenGetUsername_thenReturnUsername() {
        assertEquals("test", account.getUsername());
    }

    @Test
    void givenAccount_whenGetPassword_thenReturnPassword() {
        assertEquals("test", account.getPassword());
    }

    @Test
    void givenAccount_whenGetEmail_thenReturnEmail() {
        assertEquals("test@test.com", account.getEmail());
    }

    @Test
    void givenAccount_whenGetRole_thenReturnRole() {
        assertNotNull(account.getRole());
    }
}