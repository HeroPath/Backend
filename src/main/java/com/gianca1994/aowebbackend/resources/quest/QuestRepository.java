package com.gianca1994.aowebbackend.resources.quest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: QuestRepository
 */

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    Quest findByName(String name);
    boolean existsByName(String name);
}

