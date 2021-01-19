package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.enums.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.mapper.UserGroupMapper;
import com.lens.chatter.model.dto.user.UserGroupDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.UserGroupRepository;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;

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

    @Autowired
    private UserService userService;

    @Override
    public ChatterRepository<UserGroup, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<UserGroupDto, UserGroup, UserGroupResource> getConverter() {
        return mapper;
    }

    @Transactional
    public UserGroupResource addUsers(UUID groupId, List<UUID> userIds) {

        UserGroup group = fromIdToEntity(groupId);
        Set<User> users = group.getUsers();
        users.addAll(userRepository.findByIdIn(userIds));
        group.setUsers(users);
        return mapper.toResource(repository.save(group));
    }

    @Transactional
    public UserGroupResource removeUsers(UUID groupId, List<UUID> userIds) {
        UserGroup group = fromIdToEntity(groupId);
        Set<User> users = group.getUsers();
        users.removeAll(userRepository.findByIdIn(userIds));
        group.setUsers(users);
        return mapper.toResource(repository.save(group));
    }

    public List<UserGroupResource> getMyUserGroups(UUID userId) {
        return mapper.toResources(repository.findByUsers(userService.fromIdToEntity(userId)));
    }

    @Override
    protected void updateOperationsBeforeConvert(UserGroup entity, UserGroupDto dto, UUID userId) {
        User operator = userService.fromIdToEntity(userId);
        if (!dto.getManagerId().equals(userId) || operator.getRole().toValue() < Role.BRANCH_ADMIN.toValue()) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
    }

    @Override
    protected UserGroup saveOperations(UserGroup entity, UserGroupDto userGroupDto, UUID userId) {
        if (entity.getManager() == null) {
            entity.setManager(userService.fromIdToEntity(userId));
        }
        return entity;
    }
}
