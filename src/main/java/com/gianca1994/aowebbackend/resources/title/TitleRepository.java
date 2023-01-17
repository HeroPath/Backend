package com.gianca1994.aowebbackend.resources.title;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: This is the repository for the Title entity.
 */

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
}
