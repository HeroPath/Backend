package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.resources.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author: Gianca1994
 * Explanation: UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
