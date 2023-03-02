package com.gianca1994.heropathbackend.resources.mail.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for sending mail
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendMailDTO {
    private String receiver;
    private String subject;
    private String message;
}
