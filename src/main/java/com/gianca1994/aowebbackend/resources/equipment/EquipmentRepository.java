package com.gianca1994.aowebbackend.resources.equipment;


import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
