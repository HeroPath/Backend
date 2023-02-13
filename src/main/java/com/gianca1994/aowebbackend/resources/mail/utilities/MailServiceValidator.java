package com.gianca1994.aowebbackend.resources.mail.utilities;

import com.gianca1994.aowebbackend.exception.Conflict;

public class MailServiceValidator {

    public void userExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict(MailConst.USER_NOT_FOUND);
    }

    public void receiverNotEmpty(String receiver) throws Conflict {
        if (receiver.isEmpty()) throw new Conflict(MailConst.RECEIVER_EMPTY);
    }

    public void subjectNotEmpty(String subject) throws Conflict {
        if (subject.isEmpty()) throw new Conflict(MailConst.SUBJECT_EMPTY);
    }

    public void messageNotEmpty(String message) throws Conflict {
        if (message.isEmpty()) throw new Conflict(MailConst.MESSAGE_EMPTY);
    }
}
