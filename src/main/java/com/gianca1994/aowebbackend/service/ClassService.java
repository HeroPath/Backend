package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.model.Class;
import com.gianca1994.aowebbackend.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: ClassService
 */

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public List<Class> getAllClasses() {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the classes from the database.
         * @param none
         * @return List<Class>
         */
        return classRepository.findAll();
    }
}
