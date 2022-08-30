package com.gianca1994.aowebbackend.repository;

import com.gianca1994.aowebbackend.model.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: QuestRepository
 */

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    Quest findByName(String name);
}
