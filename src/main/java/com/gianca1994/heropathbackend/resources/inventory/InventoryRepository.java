package com.gianca1994.heropathbackend.resources.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * @Explanation: This is the repository for the Inventory entity.
 */

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
