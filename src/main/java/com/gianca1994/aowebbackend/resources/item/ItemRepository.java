package com.gianca1994.aowebbackend.resources.item;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of handling the requests related to the items.
 */

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);

    boolean existsById(@NonNull Long id);
    boolean existsByName(String name);

    List<Item> findByClassRequiredOrderByLvlMinAsc(String aClass);
}
