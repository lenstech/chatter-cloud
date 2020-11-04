package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.dto.user.InviteMailDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtGenerator;
import com.lens.chatter.util.MailUtil;
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
                        + CLIENT_URL
                        + CONFIRM_ACCOUNT_URL);
    }

    public void sendResetPasswordTokenToMail(String email) {
        if (email == null) {
            throw new BadRequestException(ErrorConstants.PROVIDE_VALID_MAIL);
        }
        User user = userRepository.findByEmail(email);
        String resetToken = jwtGenerator.generateResetPasswordToken(user.getId());
        mailUtil.sendTokenMail(user.getEmail(), resetToken, RESET_PASSWORD_HEADER,
                RESET_PASSWORD_BODY + "\n"
                        + CLIENT_URL
                        + RESET_PASSWORD_URL);

    }

    public void sendInviteTokenToMail(UUID senderId, InviteMailDto inviteMailDto) {
        //todo: sender bilgileri eklenecek.
        String email = inviteMailDto.getMail();
        if (email == null) {
            throw new BadRequestException(ErrorConstants.PROVIDE_VALID_MAIL);
        }
        UUID departmentId = inviteMailDto.getDepartmentId();
        if (departmentId == null) {
            throw new BadRequestException(ErrorConstants.ID_CANNOT_BE_EMPTY);
        }
        // title and role have default values, so there is no need to check them.

        mailUtil.sendTokenMail(inviteMailDto.getMail(),
                jwtGenerator.generateInviteMailToken(email, inviteMailDto.getRole(), inviteMailDto.getTitle(), inviteMailDto.getDepartmentId()),
                CONFIRM_ACCOUNT_HEADER,
                CONFIRM_ACCOUNT_BODY + "\n" + CLIENT_URL + CONFIRM_ACCOUNT_URL);
    }
}

