package com.gianca1994.aowebbackend.resources.mail.utilities;

import com.gianca1994.aowebbackend.exception.Conflict;

/**
 * @Author: Gianca1994
 * Explanation: This class contains all the methods that validate the data
 */

public class MailServiceValidator {

    public void userExist(boolean exist) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the user exists
         * @param boolean exist
         * @return void
         */
        if (!exist) throw new Conflict(MailConst.USER_NOT_FOUND);
    }

    public void receiverNotEmpty(String receiver) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the receiver is empty
         * @param String receiver
         * @return void
         */
        if (receiver.isEmpty()) throw new Conflict(MailConst.RECEIVER_EMPTY);
    }

    public void subjectNotEmpty(String subject) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the subject is empty
         * @param String subject
         * @return void
         */
        if (subject.isEmpty()) throw new Conflict(MailConst.SUBJECT_EMPTY);
    }

    public void messageNotEmpty(String message) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method checks if the message is empty
         * @param String message
         * @return void
         */
        if (message.isEmpty()) throw new Conflict(MailConst.MESSAGE_EMPTY);
    }

    public void mailExist(boolean exist) throws Conflict {
        /**
         *
         */
        if (!exist) throw new Conflict(MailConst.MAIL_NOT_FOUND);
    }

    public void mailBelongToUser(String username, String receiver) throws Conflict {
        /**
         *
         */
        if (!username.equals(receiver)) throw new Conflict(MailConst.MAIL_NOT_FOUND);
    }
}