package com.gianca1994.aowebbackend.resources.classes;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClassServiceTest {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    public ClassService classService;

    @BeforeEach
    void setUp() {
        classRepository.deleteAll();
        classRepository.save(new Class(1L, "testClass", 1, 1, 1, 1, 1));
    }

    @Test
    public void givenClasses_whenGetAllClasses_thenReturnAllClasses() {
        assertThat(classService.getAllClasses()).hasSize(1);
    }
}