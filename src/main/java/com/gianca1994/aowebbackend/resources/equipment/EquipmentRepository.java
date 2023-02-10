package com.gianca1994.aowebbackend.resources.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: This is the repository for the Equipment entity.
 */

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
