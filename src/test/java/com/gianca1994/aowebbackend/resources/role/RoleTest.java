package com.gianca1994.aowebbackend.resources.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RoleTest {

    private Role roleTest;

    @BeforeEach
    void setUp() {
        roleTest = new Role();
        roleTest.setId(1L);
        roleTest.setRoleName("ROLE_TEST");
    }

    @Test
    public void constructorNotArgsTest() {
        Role roleTest = new Role();
        assertThat(roleTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Role roleTest2 = new Role(2L, "ROLE_TEST2");
        assertThat(roleTest2).isNotNull();
    }

    @Test
    void givenRole_whenGetId_thenReturnId() {
        assertThat(roleTest.getId()).isEqualTo(1L);
    }

    @Test
    void givenRole_whenGetRoleName_thenReturnRoleName() {
        assertThat(roleTest.getRoleName()).isEqualTo("ROLE_TEST");
    }

    @Test
    void givenRole_whenSetId_thenSetId() {
        roleTest.setId(2L);
        assertThat(roleTest.getId()).isEqualTo(2L);
    }

    @Test
    void givenRole_whenSetRoleName_thenSetRoleName() {
        roleTest.setRoleName("ROLE_TEST2");
        assertThat(roleTest.getRoleName()).isEqualTo("ROLE_TEST2");
    }
}