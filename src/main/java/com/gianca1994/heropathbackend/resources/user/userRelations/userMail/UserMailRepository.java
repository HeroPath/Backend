package com.gianca1994.heropathbackend.resources.user.userRelations.userMail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to create the UserMail table in the database.
 */

@Repository
public interface UserMailRepository extends JpaRepository<UserMail, Long> {
    @Modifying
    @Query("DELETE FROM UserMail um WHERE um.user.id = :userId AND um.mail.id = :mailId")
    void deleteByUserIdAndMailId(@Param("userId") Long userId, @Param("mailId") Long mailId);

    @Modifying
    @Query("DELETE FROM UserMail um WHERE um.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    boolean existsByUserId(Long userId);
}