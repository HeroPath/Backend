package com.gianca1994.aowebbackend.resources.classes;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to manage the classes.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/classes")
public class ClassController {

    @GetMapping()
    public List<Class> getAllClasses() throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the classes.
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
