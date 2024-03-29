package com.lens.chatter.util;

import com.lens.chatter.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.lens.chatter.constant.ErrorConstants.MAIL_SEND_FAILED;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTokenMail(String email, String activationToken, String subject, String text) {
        sendMail(email, subject, String.format(text, activationToken));
    }

    public void sendMail(String email, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress(email));
            mimeMessage.setText(text, "UTF-8");
            mimeMessage.setSubject(subject, "UTF-8");
        } catch (MessagingException messagingException) {
            throw new BadRequestException(MAIL_SEND_FAILED);
        }
        mailSender.send(mimeMessage);
    }
}
