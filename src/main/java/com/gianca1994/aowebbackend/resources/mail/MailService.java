package com.gianca1994.aowebbackend.resources.mail;

import com.gianca1994.aowebbackend.resources.mail.dto.request.SendMailDTO;
import com.gianca1994.aowebbackend.resources.mail.utilities.MailServiceValidator;
import com.gianca1994.aowebbackend.resources.mail.utilities.RSA;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is the service of the mail
 */

@Service
public class MailService {

    MailServiceValidator validator = new MailServiceValidator();

    @Autowired
    private MailRepository mailR;

    @Autowired
    private UserRepository userR;

    public List<Mail> getMails(String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This method returns all the mails of the user
         * @param String username
         * @return List<Mail>
         */
        List<Mail> mails = mailR.findAllByReceiver(username);
        for (Mail mail : mails) {
            RSA rsa = new RSA(userR.findByUsername(mail.getReceiver()).getRsaPublicKey(), userR.findByUsername(mail.getReceiver()).getRsaPrivateKey());
            mail.setMessage(rsa.decrypt(mail.getMessage()));
        }
        return mails;
    }

    public void sendMail(String username, SendMailDTO mail) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method sends a mail to the receiver
         * @param String username
         * @param SendMailDTO mail
         * @return void
         */
        validator.receiverNotEmpty(mail.getReceiver());
        validator.subjectNotEmpty(mail.getSubject());
        validator.messageNotEmpty(mail.getMessage());
        validator.userExist(userR.existsByUsername(username));
        validator.userExist(userR.existsByUsername(mail.getReceiver()));

        User receiver = userR.findByUsername(mail.getReceiver());

        RSA rsa = new RSA(receiver.getRsaPublicKey(), receiver.getRsaPrivateKey());
        //String encryptedMessage = rsa.encrypt(mail.getMessage());
        //System.out.println(encryptedMessage);
        //String decryptedMessage = rsa.decrypt(encryptedMessage);
        //System.out.println(decryptedMessage);

        Mail newMail = new Mail(username, receiver.getUsername(), mail.getSubject(), rsa.encrypt(mail.getMessage()));
        receiver.getMail().add(newMail);
        mailR.save(newMail);
        userR.save(receiver);
    }
}
