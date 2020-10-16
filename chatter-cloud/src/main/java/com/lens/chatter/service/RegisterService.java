package com.lens.chatter.service;

import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.dto.user.SendInviteMailDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.CompleteUserResource;
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
import static com.lens.chatter.constant.MailConstants.*;


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

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Transactional
    public CompleteUserResource register(RegisterDto registerDto) {
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
        userRepository.saveAndFlush(user);
        confirmationTokenService.sendActivationToken(user);
        return mapper.toResource(user);
    }

    public void sendInviteMail(UUID senderId, SendInviteMailDto inviteMailDto) {
        mailUtil.sendMail(inviteMailDto.getMail(),
                jwtGenerator.generateInviteMailToken(inviteMailDto.getMail(), inviteMailDto.getRole(), inviteMailDto.getTitle()),
                CONFIRM_ACCOUNT_HEADER,
                CONFIRM_ACCOUNT_BODY + "\n" + CLIENT_URL + CONFIRM_ACCOUNT_URL);

    }

    @Transactional
    public void confirmRegister(String confirmationToken) {
        UUID id = jwtResolver.getIdFromToken(confirmationToken);
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new BadRequestException(ErrorConstants.USER_NOT_EXIST);
        }
        user.setConfirmed(true);
        userRepository.save(user);
    }

}
