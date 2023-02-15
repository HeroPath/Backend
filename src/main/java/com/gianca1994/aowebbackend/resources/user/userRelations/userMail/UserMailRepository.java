package com.gianca1994.aowebbackend.resources.user.userRelations.userMail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserMailRepository extends JpaRepository<UserMail, Long> {
    Set<UserMail> findByUserUsername(String username);
}
