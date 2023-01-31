package com.gianca1994.aowebbackend.resources.guild;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This is the repository for the Guild class.
 */

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    Guild findByName(String name);
    List<Guild> findAllByOrderByTitlePointsDesc();
}

