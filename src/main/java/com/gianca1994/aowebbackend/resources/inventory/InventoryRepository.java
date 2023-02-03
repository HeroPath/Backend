package com.gianca1994.aowebbackend.resources.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: Interface
 */

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
