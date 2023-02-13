package com.gianca1994.aowebbackend.resources.mail;

import com.gianca1994.aowebbackend.resources.mail.dto.request.SendMailDTO;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private MailRepository mailR;

    @Autowired
    private UserRepository userR;

    public List<Mail> getMails() {
        return mailR.findAll();
    }

    public void sendMail(String username, SendMailDTO mail) {
        User receiver = userR.findByUsername(mail.getReceiver());

        Mail newMail = new Mail(username, mail.getSubject(), mail.getMessage());
        mailR.save(newMail);

        receiver.getMail().add(newMail);
        userR.save(receiver);
    }
}
