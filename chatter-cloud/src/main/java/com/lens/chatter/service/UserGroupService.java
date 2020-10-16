package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.UserGroupMapper;
import com.lens.chatter.model.dto.user.UserGroupDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.UserGroupRepository;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.*;

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

    @Autowired
    private UserRepository userRepository;

    @Override
    public ChatterRepository<UserGroup, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<UserGroupDto, UserGroup, UserGroupResource> getConverter() {
        return mapper;
    }

    @Override
    public UserGroupResource put(UUID id, UserGroupDto updatedDto, UUID userId) {
        UserGroup theReal = repository.findById(id).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        if (updatedDto == null) {
            throw new BadRequestException(DTO_CANNOT_BE_EMPTY);
        }
        if (id == null) {
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        UserGroup updated = mapper.toEntity(updatedDto);
        updated.setId(theReal.getId());
        updated.setUsers(theReal.getUsers());
        updated.setCreatedDate(theReal.getCreatedDate());
        return mapper.toResource(getRepository().save(updated));
    }

    @Transactional
    public UserGroupResource addUsers(UUID groupId, List<UUID> userIds) {
        UserGroup group = repository.findById(groupId).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        Set<User> users = group.getUsers();
        users.addAll(userRepository.findByIdIn(userIds));
        group.setUsers(users);
        return mapper.toResource(repository.save(group));
    }

    @Transactional
    public UserGroupResource removeUsers(UUID groupId, List<UUID> userIds) {
        UserGroup group = repository.findById(groupId).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        Set<User> users = group.getUsers();
        users.removeAll(userRepository.findByIdIn(userIds));
        group.setUsers(users);
        return mapper.toResource(repository.save(group));
    }
}
