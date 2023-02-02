package com.gianca1994.aowebbackend.resources.classes;

import com.gianca1994.aowebbackend.config.ModifConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: ClassService
 */

@Service
public class ClassService {


    public List<Class> getAllClasses() {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the classes from the database.
         * @param none
         * @return List<Class>
         */
        return Arrays.asList(ModifConfig.MAGE, ModifConfig.WARRIOR, ModifConfig.ARCHER);
    }
}
