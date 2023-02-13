package com.gianca1994.aowebbackend.resources.mail;

import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.mail.dto.request.SendMailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/mails")
public class MailController {

    @Autowired
    private MailService mailS;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Mail> getAllMails() {
        return mailS.getMails();
    }

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void sendMail(@RequestHeader(value = "Authorization") String token,
                         @RequestBody SendMailDTO mail) {
        mailS.sendMail(
                jwt.getUsernameFromToken(token.substring(7)),
                mail
        );
    }
}
