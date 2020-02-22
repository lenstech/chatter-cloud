package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadExceptionRequest;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.dto.RegisterDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.CompleteUserResource;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.MAIL_ALREADY_EXISTS;


/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserMapper mapper;

    @Transactional
    public CompleteUserResource save(RegisterDto registerDto, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = mapper.toEntity(registerDto);
        if(userRepository.existsByEmail(user.getEmail())){
            throw new BadExceptionRequest(MAIL_ALREADY_EXISTS);
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.saveAndFlush(user);
        confirmationTokenService.sendActivationToken(user);
        return mapper.toResource(user);
    }

    @Transactional
    public void confirmRegister(String confirmationToken) {
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BadExceptionRequest(ErrorConstants.USER_NOT_EXIST);
        }
        user.setConfirmed(true);
        userRepository.save(user);
    }

}
