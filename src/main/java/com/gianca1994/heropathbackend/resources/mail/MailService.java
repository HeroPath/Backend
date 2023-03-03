package com.gianca1994.heropathbackend.resources.mail;

import com.gianca1994.heropathbackend.resources.mail.utilities.AES;
import com.gianca1994.heropathbackend.resources.mail.utilities.MailServiceValidator;
import com.gianca1994.heropathbackend.resources.mail.utilities.RSA;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.resources.user.userRelations.userMail.UserMail;
import com.gianca1994.heropathbackend.resources.user.userRelations.userMail.UserMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class is the service of the mail
 */

@Service
public class MailService {

    MailServiceValidator validator = new MailServiceValidator();
    private final RSA rsa = new RSA();
    private final AES aes = new AES();

    @Autowired
    private MailRepository mailR;

    @Autowired
    private UserRepository userR;

    @Autowired
    private UserMailRepository userMailR;

    public List<Mail> getMails(String username) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method returns all the mails of the user
         * @param String username
         * @return List<Mail>
         */
        rsa.setKeys(userR.findRsaPublicK(username), aes.decryptMsg(userR.findRsaPrivateK(username)));
        List<Mail> mails = mailR.findAllByReceiver(username);
        for (Mail mail : mails) {
            mail.setMessage(rsa.decryptMsg(mail.getMessage()));
        }
        return mails;
    }

    public void sendMail(String username, String receiver, String subject, String msg) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method sends a mail to the receiver
         * @param String username
         * @param String receiver
         * @param String subject
         * @param String msg
         * @return void
         */
        validator.receiverNotEmpty(receiver);
        validator.subjectNotEmpty(subject);
        validator.messageNotEmpty(msg);
        validator.userExist(userR.existsByUsername(username));
        validator.userExist(userR.existsByUsername(receiver));
        validator.userNotEqual(username, receiver);

        User userRec = userR.findByUsername(receiver);
        rsa.setKeys(userRec.getRsaPublicKey(), userRec.getRsaPrivateKey());

        Mail newMail = new Mail(username, receiver, subject, rsa.encryptMsg(msg));
        UserMail newUserMail = new UserMail(userRec, newMail);
        mailR.save(newMail);
        userMailR.save(newUserMail);
    }

    @Transactional
    public void deleteAllMails(Long userId, String username) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method deletes all the mails of the user
         * @param Long userId
         * @param String username
         * @return void
         */
        validator.userExist(userR.existsById(userId));
        validator.userHaveMails(userMailR.existsByUserId(userId));
        userMailR.deleteByUserId(userId);
        mailR.deleteAllByReceiver(username);
    }

    @Transactional
    public void deleteMail(Long userId, Long mailId) throws Exception {
        /**
         * @Author: Gianca1994
         * @Explanation: This method deletes a mail of the user
         * @param Long userId
         * @return void
         */
        validator.userExist(userR.existsById(userId));
        validator.mailExist(mailR.existsById(mailId));
        userMailR.deleteByUserIdAndMailId(userId, mailId);
        mailR.delete(mailR.findById(mailId).get());
    }
}