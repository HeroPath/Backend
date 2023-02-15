package com.gianca1994.aowebbackend.resources.mail;

import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.mail.dto.request.SendMailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to manage the mails
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/mails")
public class MailController {

    @Autowired
    private MailService mailS;

    @Autowired
    private JwtTokenUtil jwt;

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Mail> getAllMails(@RequestHeader(value = "Authorization") String token) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to get all the mails of the user
         * @param String token
         * @return List<Mail>
         */
        return mailS.getMails(jwt.getUsernameFromToken(token.substring(7)));
    }

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void sendMail(@RequestHeader(value = "Authorization") String token,
                         @RequestBody SendMailDTO mail) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to send a mail to another user
         * @param String token
         * @param SendMailDTO mail
         * @return void
         */
        mailS.sendMail(
                jwt.getUsernameFromToken(token.substring(7)),
                mail.getReceiver(), mail.getSubject(), mail.getMessage()
        );
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public void deleteAllMails(@RequestHeader(value = "Authorization") String token) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to delete all the mails of the user
         * @param String token
         * @return void
         */
        mailS.deleteAllMails(
                jwt.getIdFromToken(token.substring(7)),
                jwt.getUsernameFromToken(token.substring(7))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Mail> deleteMail(@RequestHeader(value = "Authorization") String token,
                                 @PathVariable("id") Long id) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to delete a mail
         * @param String token
         * @param Long id
         * @return List<Mail>
         */
        return mailS.deleteMail(
                jwt.getUsernameFromToken(token.substring(7)),
                jwt.getIdFromToken(token.substring(7)),
                id
        );
    }
}
