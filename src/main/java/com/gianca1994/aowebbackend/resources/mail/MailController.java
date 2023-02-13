package com.gianca1994.aowebbackend.resources.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/mails")
public class MailController {

    @Autowired
    private MailService mailS;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Mail> getAllMails() {
        return mailS.getMails();
    }
}
