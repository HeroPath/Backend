package com.gianca1994.aowebbackend.resources.classes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClassControllerTest {

    @Autowired
    private ClassController classController;

    @Test
    void givenClasses_whenGetAllClasses_thenReturnAllClasses() throws UnsupportedEncodingException {

        assertEquals(3, classController.getAllClasses().size());
    }
}