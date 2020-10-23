package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.exception.NotFoundException;
import com.lens.chatter.mapper.MinimalUserMapper;
import com.lens.chatter.mapper.UserMapper;
import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.CompleteUserResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 3 May 2020
 */

@Service
public class UserService extends AbstractService<User, UUID, RegisterDto, CompleteUserResource> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private MinimalUserMapper minimalUserMapper;


    @Override
    public UserRepository getRepository() {
        return repository;
    }

    @Override
    public Converter<RegisterDto, User, CompleteUserResource> getConverter() {
        return mapper;
    }

    public MinimalUserResource findUserByIdToMinRes(UUID id) {
        return minimalUserMapper.toResource(fromIdToEntity(id));
    }
}
