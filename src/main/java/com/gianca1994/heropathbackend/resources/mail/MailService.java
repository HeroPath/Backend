package com.gianca1994.heropathbackend.resources.mail;

import com.gianca1994.heropathbackend.resources.mail.encryption.AES;
import com.gianca1994.heropathbackend.resources.user.User;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import com.gianca1994.heropathbackend.resources.user.userRelations.userMail.UserMail;
import com.gianca1994.heropathbackend.resources.user.userRelations.userMail.UserMailRepository;
import com.gianca1994.heropathbackend.utils.Validator;
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

    Validator validate = new Validator();
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
        List<Mail> mails = mailR.findAllByReceiver(username);
        for (Mail mail : mails) {
            mail.setMessage(aes.decryptMsg(mail.getMessage()));
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
        validate.receiverNotEmpty(receiver);
        validate.subjectNotEmpty(subject);
        validate.messageNotEmpty(msg);
        validate.userExist(userR.existsByUsername(username));
        validate.userExist(userR.existsByUsername(receiver));
        validate.userNotEqual(username, receiver);

        User userRec = userR.findByUsername(receiver);

        Mail newMail = new Mail(username, receiver, subject, aes.encryptMsg(msg));
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
        validate.userExist(userR.existsById(userId));
        validate.userHaveMails(userMailR.existsByUserId(userId));
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
        validate.userExist(userR.existsById(userId));
        validate.mailExist(mailR.existsById(mailId));
        userMailR.deleteByUserIdAndMailId(userId, mailId);
        mailR.delete(mailR.findById(mailId).get());
    }
}