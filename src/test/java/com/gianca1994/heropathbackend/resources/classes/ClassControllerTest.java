package com.gianca1994.heropathbackend.resources.classes;

import com.gianca1994.heropathbackend.exception.Conflict;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClassControllerTest {

    @Autowired
    private ClassController classController;

    @Test
    void givenClasses_whenGetAllClasses_thenReturnAllClasses() throws Conflict {
        assertEquals(3, classController.getAllClasses().size());
    }
}