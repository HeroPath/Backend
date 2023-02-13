package com.gianca1994.aowebbackend.resources.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private MailRepository mailR;

    public List<Mail> getMails() {
        return mailR.findAll();
    }

}
