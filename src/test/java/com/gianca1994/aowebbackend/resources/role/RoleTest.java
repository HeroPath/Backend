package com.gianca1994.aowebbackend.resources.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RoleTest {

    private Role roleTest;

    @BeforeEach
    void setUp() {
        roleTest = new Role();
        roleTest.setRoleName("ROLE_TEST");
    }

    @Test
    public void constructorNotArgsTest() {
        Role roleTest = new Role();
        assertThat(roleTest).isNotNull();
    }

    @Test
    public void constructorAllArgsTest() {
        Role roleTest2 = new Role("ROLE_TEST2");
        assertThat(roleTest2).isNotNull();
    }

    @Test
    void givenRole_whenGetRoleName_thenReturnRoleName() {
        assertThat(roleTest.getRoleName()).isEqualTo("ROLE_TEST");
    }
}