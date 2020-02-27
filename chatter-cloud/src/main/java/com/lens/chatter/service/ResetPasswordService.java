package com.lens.chatter.service;

import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.NEW_PASSWORD_CANNOT_BE_SAME_AS_OLD;
import static com.lens.chatter.constant.ErrorConstants.USER_NOT_EXIST;

@Service
public class ResetPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JwtResolver jwtResolver;

    @Transactional
    public void resetPassword(String password, String confirmationToken) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        if(encoder.matches(password,user.getPassword())){
            throw new BadRequestException(NEW_PASSWORD_CANNOT_BE_SAME_AS_OLD);
        }
        user.setPassword(encoder.encode(password));
        user.setConfirmed(true);
        userRepository.save(user);
    }

}
