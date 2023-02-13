package com.gianca1994.aowebbackend.resources.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "mails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    @JsonIgnore
    private String receiver;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String message;

    public Mail(String sender, String receiver, String subject, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
    }
}
