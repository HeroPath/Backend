package com.gianca1994.heropathbackend.resources.mail.utilities;

import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.utils.Const;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the methods that validate the data
 */

public class MailServiceValidator {

    public void userExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.USER.NOT_FOUND.getMsg());
    }

    public void mailExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.MAIL.NOT_FOUND.getMsg());
    }

    public void receiverNotEmpty(String receiver) throws Conflict {
        if (receiver.isEmpty()) throw new Conflict(Const.MAIL.RECEIVER_EMPTY.getMsg());
    }

    public void subjectNotEmpty(String subject) throws Conflict {
        if (subject.isEmpty()) throw new Conflict(Const.MAIL.SUBJECT_EMPTY.getMsg());
    }

    public void messageNotEmpty(String message) throws Conflict {
        if (message.isEmpty()) throw new Conflict(Const.MAIL.MSG_EMPTY.getMsg());
    }

    public void userNotEqual(String username, String receiver) throws Conflict {
        if (username.equals(receiver)) throw new Conflict(Const.MAIL.USER_NOT_EQUAL.getMsg());
    }

    public void userHaveMails(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(Const.MAIL.USER_NOT_HAVE_MAILS.getMsg());
    }
}