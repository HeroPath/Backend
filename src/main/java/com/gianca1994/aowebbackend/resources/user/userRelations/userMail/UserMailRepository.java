package com.gianca1994.aowebbackend.resources.user.userRelations.userMail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMailRepository extends JpaRepository<UserMail, Long> {
    @Modifying
    @Query("DELETE FROM UserMail um WHERE um.user.id = :userId AND um.mail.id = :mailId")
    void deleteByUserIdAndMailId(@Param("userId") Long userId, @Param("mailId") Long mailId);
}
