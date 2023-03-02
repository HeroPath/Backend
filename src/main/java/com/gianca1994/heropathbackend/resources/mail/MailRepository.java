package com.gianca1994.heropathbackend.resources.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This interface is used to make the connection with the database
 */

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findAllByReceiver(String receiver);

    @Modifying
    @Query("DELETE FROM Mail m WHERE m.receiver = :receiver")
    void deleteAllByReceiver(@Param("receiver") String receiver);

}
