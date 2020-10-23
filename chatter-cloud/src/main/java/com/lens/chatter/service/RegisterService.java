package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.InviteMailMapper;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.dto.user.InviteMailDto;
import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.InviteMailResource;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.repository.DepartmentRepository;
import com.lens.chatter.repository.UserRepository;
import com.lens.chatter.security.JwtGenerator;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.ID_IS_NOT_EXIST;
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
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private InviteMailMapper inviteMailMapper;

    @Transactional
    public LoginResource register(RegisterDto registerDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = mapper.toEntity(registerDto);
        if (registerDto.getDepartmentId() != null) {
            Department department = departmentRepository.findDepartmentById(registerDto.getDepartmentId());
            if (department == null) {
                throw new BadRequestException(ID_IS_NOT_EXIST);
            }
            user.setDepartment(department);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException(MAIL_ALREADY_EXISTS);
        }
        user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        user.setConfirmed(true);
        userRepository.saveAndFlush(user);
//        tokenService.sendActivationTokenToMail(user);
        return new LoginResource(mapper.toResource(user), jwtGenerator.generateLoginToken(user.getId(), user.getRole()));
    }


    @Transactional
    public void confirmRegister(String confirmationToken) {
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userService.fromIdToEntity(id);
        if (user == null) {
            throw new BadRequestException(ErrorConstants.USER_NOT_EXIST);
        }
        user.setConfirmed(true);
        userRepository.save(user);
    }

    public void sendInviteMail(String token, InviteMailDto inviteMailDto) {
        UUID senderId = jwtResolver.getIdFromToken(token);
        if (userRepository.existsByEmail(inviteMailDto.getMail())) {
            throw new BadRequestException(MAIL_ALREADY_EXISTS);
        }
        tokenService.sendInviteTokenToMail(senderId, inviteMailDto);
    }

    public InviteMailResource getInviteTokenInfo(String inviteToken) {
        return inviteMailMapper.DtoToResource(jwtResolver.getInviteTokensInfo(inviteToken));
    }
}
