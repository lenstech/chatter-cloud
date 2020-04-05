package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.mapper.UserGroupMapper;
import com.lens.chatter.model.dto.UserGroupDto;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */
@Service
public class UserGroupService extends AbstractService<UserGroup, UUID, UserGroupDto, UserGroupResource> {
    @Autowired
    private UserGroupRepository repository;

    @Autowired
    private UserGroupMapper mapper;

    @Override
    public JpaRepository<UserGroup, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<UserGroupDto, UserGroup, UserGroupResource> getConverter() {
        return mapper;
    }
}
