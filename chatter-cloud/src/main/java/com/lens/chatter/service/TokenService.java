package com.lens.chatter.service;

import com.lens.chatter.model.dto.user.InviteMailDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtGenerator;
import com.lens.chatter.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.lens.chatter.constant.MailConstants.*;


/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class TokenService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailUtil mailUtil;

    public void sendActivationTokenToMail(User user) {
        String confirmationToken = jwtGenerator.generateMailConfirmationToken(user.getId());
        mailUtil.sendTokenMail(user.getEmail(), confirmationToken, CONFIRM_ACCOUNT_HEADER,
                CONFIRM_ACCOUNT_BODY
                        + this.environment.getProperty("spring.url")
                        + CONFIRM_ACCOUNT_URL);
    }

    public void sendResetPasswordTokenToMail(String email) {
        User user = userRepository.findByEmail(email);
        String resetToken = jwtGenerator.generateResetPasswordToken(user.getId());
        mailUtil.sendTokenMail(user.getEmail(), resetToken, RESET_PASSWORD_HEADER,
                RESET_PASSWORD_BODY
                        + this.environment.getProperty("spring.url")
                        + RESET_PASSWORD_URL);

    }

    public void sendInviteTokenToMail(UUID senderId, InviteMailDto inviteMailDto) {
        mailUtil.sendTokenMail(inviteMailDto.getMail(),
                jwtGenerator.generateInviteMailToken(inviteMailDto.getMail(), inviteMailDto.getRole(), inviteMailDto.getTitle()),
                CONFIRM_ACCOUNT_HEADER,
                CONFIRM_ACCOUNT_BODY + "\n" + CLIENT_URL + CONFIRM_ACCOUNT_URL);
    }
}

