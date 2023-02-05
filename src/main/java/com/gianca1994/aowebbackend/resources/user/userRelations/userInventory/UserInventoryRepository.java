package com.gianca1994.aowebbackend.resources.user.userRelations.userInventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {
    UserInventory findByUserId(Long userId);
}
