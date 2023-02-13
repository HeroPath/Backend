package com.gianca1994.aowebbackend.resources.mail.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMailDTO {
    private String receiver;
    private String subject;
    private String message;
}
