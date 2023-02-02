package com.gianca1994.aowebbackend.resources.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author: Gianca1994
 * Explanation: UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    // Page List ordered by level, title, points and experience
    Page<User> findAllByOrderByLevelDescTitlePointsDescExperienceDesc(PageRequest of);

}
