package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.mapper.LoginMapper;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.dto.user.LoginDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.CompleteUserResource;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtGenerator;
import com.lens.chatter.security.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginMapper mapper;

    @Autowired
    private UserMapper userMapper;

    public LoginResource login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (user != null && encoder.matches(dto.getPassword(), user.getPassword())) {
            if (!user.isConfirmed()) {
                throw new UnauthorizedException(ErrorConstants.PLEASE_CONFIRM_YOUR_EMAIL_ADDRESS);
            }
            String token = jwtGenerator.generateLoginToken(user.getId(), user.getRole());
            CompleteUserResource userResource = userMapper.toResource(user);
            return new LoginResource(userResource, token);
        } else {
            throw new UnauthorizedException(ErrorConstants.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    public String updateToken(String token) {
        return jwtGenerator.generateLoginToken(jwtResolver.getIdFromToken(token), jwtResolver.getRoleFromToken(token));
    }
}
