package com.gianca1994.aowebbackend.repository;

import com.gianca1994.aowebbackend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
