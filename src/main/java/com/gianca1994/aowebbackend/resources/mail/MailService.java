package com.gianca1994.aowebbackend.resources.mail;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.mail.dto.request.SendMailDTO;
import com.gianca1994.aowebbackend.resources.mail.utilities.MailServiceValidator;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    MailServiceValidator validator = new MailServiceValidator();

    @Autowired
    private MailRepository mailR;

    @Autowired
    private UserRepository userR;

    public List<Mail> getMails(String username) {
        return mailR.findAllByReceiver(username);
    }

    public void sendMail(String username, SendMailDTO mail) throws Conflict {

        validator.receiverNotEmpty(mail.getReceiver());
        validator.subjectNotEmpty(mail.getSubject());
        validator.messageNotEmpty(mail.getMessage());
        validator.userExist(userR.existsByUsername(username));
        validator.userExist(userR.existsByUsername(mail.getReceiver()));

        User receiver = userR.findByUsername(mail.getReceiver());
        Mail newMail = new Mail(username, receiver.getUsername(), mail.getSubject(), mail.getMessage());
        receiver.getMail().add(newMail);
        mailR.save(newMail);
        userR.save(receiver);
    }
}
