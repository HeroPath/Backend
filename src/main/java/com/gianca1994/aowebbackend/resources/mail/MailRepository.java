package com.gianca1994.aowebbackend.resources.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findAllByReceiver(String receiver);
}
