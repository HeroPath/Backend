package com.gianca1994.aowebbackend.controller;

import com.gianca1994.aowebbackend.model.Class;
import com.gianca1994.aowebbackend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping()
    public List<Class> getAllClasses() {
        return classService.getAllClasses();
    }
}
