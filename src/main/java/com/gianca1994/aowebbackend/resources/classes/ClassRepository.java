package com.gianca1994.aowebbackend.resources.classes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: ClassRepository
 */

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
}
