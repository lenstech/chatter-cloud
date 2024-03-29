package com.lens.chatter.service;

import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.MinimalUserMapper;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.dto.user.UpdatePasswordDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.CompleteUserResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.*;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MinimalUserMapper minimalUserMapper;

    public CompleteUserResource getSelfProfile(UUID selfId) {
        User selfUser = userService.fromIdToEntity(selfId);
        if (selfUser == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        } else {
            return userMapper.toResource(selfUser);
        }
    }

    public MinimalUserResource getOtherProfile(String email) {
        User otherUser = userRepository.findByEmail(email);
        if (otherUser == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        return minimalUserMapper.toResource(otherUser);
    }

    @Transactional
    public CompleteUserResource updateProfile(UUID userId, RegisterDto dto) {
        User oldUser = userService.fromIdToEntity(userId);
        if (oldUser == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        User updatedUser = userMapper.toEntity(dto);
        updatedUser.setId(oldUser.getId());
        updatedUser.setPassword(oldUser.getPassword());
        updatedUser.setCreatedDate(oldUser.getCreatedDate());
        userRepository.save(updatedUser);
        return userMapper.toResource(updatedUser);
    }

    @Transactional
    public CompleteUserResource updatePassword(UUID userId, UpdatePasswordDto dto) {
        User user = userService.fromIdToEntity(userId);
        if (user == null) {
            throw new BadRequestException(USER_NOT_EXIST);
        }
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if (!encoder.matches(dto.getOldPassword(),user.getPassword())){
            throw new BadRequestException(OLD_PASSWORD_IS_WRONG);
        }
        String newPassword = dto.getNewPassword();
        if (encoder.matches(newPassword,user.getPassword())){
            throw new BadRequestException(NEW_PASSWORD_CANNOT_BE_SAME_AS_OLD);
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return userMapper.toResource(user);
    }

}
