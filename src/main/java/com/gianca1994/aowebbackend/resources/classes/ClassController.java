package com.gianca1994.aowebbackend.resources.classes;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
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

    @GetMapping()
    public List<Class> getAllClasses() throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the classes from the database.
         * @param none
         * @return List<Class>
         */
        try {
            return ModifConfig.CLASSES;
        } catch (Exception e) {
            throw new Conflict("Error while getting all classes");
        }
    }
}
