package com.lens.chatter.service;

import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtGenerator;
import com.lens.chatter.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.*;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Transactional
    public void resetPasswordRequest(String email) {
        tokenService.sendResetPasswordTokenToMail(email);
    }

    @Transactional
    @Modifying
    public LoginResource changePassword(String password, UUID userId) {
        User user = userService.fromIdToEntity(userId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(password, user.getPassword())) {
            throw new BadRequestException(NEW_PASSWORD_CANNOT_BE_SAME_AS_OLD);
        }
        user.setPassword(encoder.encode(password));
        user.setConfirmed(true);
        userRepository.save(user);
        return new LoginResource(userMapper.toResource(user), jwtGenerator.generateLoginToken(user.getId(), user.getRole()));
    }

    @Transactional
    public void changePasswordByAdmin(String email, String newPassword, String token) {
        User admin = userService.fromIdToEntity(jwtResolver.getIdFromToken(token));
        User user = userRepository.findByEmail(email);
        if (admin == null || user == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        if (user.getRole().toValue() > 3) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
}
