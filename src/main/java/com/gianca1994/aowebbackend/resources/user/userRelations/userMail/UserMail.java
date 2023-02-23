package com.gianca1994.aowebbackend.resources.user.userRelations.userMail;


import com.gianca1994.aowebbackend.resources.mail.Mail;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to create the UserMail table in the database.
 */

@Entity
@Table(name = "user_mails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMail {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mail_id")
    private Mail mail;

    public UserMail(User user, Mail mail) {
        this.user = user;
        this.mail = mail;
    }
}
