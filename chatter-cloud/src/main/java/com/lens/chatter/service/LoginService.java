package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.mapper.LoginMapper;
import com.lens.chatter.model.dto.LoginDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginMapper mapper;


    public LoginResource login(LoginDto loginDto) throws IllegalAccessException {
        User user = userRepository.findByEmail(loginDto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(loginDto.getPassword(), user.getPassword())) {
            if(!user.isConfirmed()){
                throw new IllegalAccessException(ErrorConstants.PLEASE_CONFIRM_YOUR_EMAIL_ADDRESS);
            }
            String token = jwtGenerator.generateToken(user.getId(), user.getRole());
            LoginResource resource = mapper.toResource(user);
            resource.setToken(token);
            return resource;
        } else {
            throw new IllegalAccessException(ErrorConstants.WRONG_EMAIL_OR_PASSWORD);
        }
    }
}
