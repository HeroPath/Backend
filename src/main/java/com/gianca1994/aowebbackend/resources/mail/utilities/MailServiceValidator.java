package com.gianca1994.aowebbackend.resources.mail.utilities;

import com.gianca1994.aowebbackend.exception.Conflict;

public class MailServiceValidator {

    public void userExist(boolean exist) throws Conflict {
        if (!exist) throw new Conflict("User not found");
    }

    public void receiverNotEmpty(String receiver) throws Conflict {
        if (receiver.isEmpty()) throw new Conflict("Receiver can't be empty");
    }

    public void subjectNotEmpty(String subject) throws Conflict {
        if (subject.isEmpty()) throw new Conflict("Subject can't be empty");
    }

    public void messageNotEmpty(String message) throws Conflict {
        if (message.isEmpty()) throw new Conflict("Message can't be empty");
    }
}
