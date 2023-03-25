package com.gianca1994.heropathbackend.resources.quest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: QuestRepository is a repository that extends JpaRepository
 */

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    Quest findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT q FROM Quest q WHERE q.levelRequired <= :userLevel")
    List<Quest> findAllAvailableQuestsForUser(@Param("userLevel") int userLevel);
}

