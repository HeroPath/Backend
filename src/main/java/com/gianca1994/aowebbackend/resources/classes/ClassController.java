package com.gianca1994.aowebbackend.resources.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: ClassController
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping()
    public List<Class> getAllClasses() {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the classes from the database.
         * @param none
         * @return List<Class>
         */
        try {
            return classService.getAllClasses();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
