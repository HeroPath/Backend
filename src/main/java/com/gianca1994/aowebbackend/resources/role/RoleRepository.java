package com.gianca1994.aowebbackend.resources.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * Explanation: RoleRepository
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
