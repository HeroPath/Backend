package com.gianca1994.aowebbackend.resources.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.resources.user.userRelations.userMail.UserMail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to create the mail object
 */

@Entity
@Table(name = "mails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    @JsonIgnore
    private String receiver;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "text", nullable = false)
    private String message;

    @OneToMany(mappedBy = "mail", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserMail> userMails;

    public Mail(String sender, String receiver, String subject, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
    }
}
