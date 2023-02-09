package com.gianca1994.aowebbackend.resources.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This is the repository for the Item class.
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);

    boolean existsByName(String name);

    // List of items by class
    List<Item> findByClassRequiredOrderByLvlMinAsc(String aClass);
}
