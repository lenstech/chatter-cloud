package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.DepartmentMapper;
import com.lens.chatter.mapper.MinimalUserMapper;
import com.lens.chatter.model.dto.DepartmentDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.DepartmentResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.repository.DepartmentRepository;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.USER_ALREADY_ADDED_TO_DEPARTMENT;
import static com.lens.chatter.constant.ErrorConstants.USER_IS_NOT_EXIST;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
@Service
public class DepartmentService extends AbstractService<Department, UUID, DepartmentDto, DepartmentResource> {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentMapper mapper;

    @Autowired
    private MinimalUserMapper userMapper;

    @Transactional
    public DepartmentResource addPersonal(UUID departmentId, UUID userId) {
        Department department = repository.findDepartmentById(departmentId);
        User user = userRepository.findUserById(userId);

        if (user.getDepartment().equals(department)) {
            throw new BadRequestException(USER_ALREADY_ADDED_TO_DEPARTMENT);
        }
        user.setDepartment(department);
        userRepository.save(user);
        return mapper.toResource(repository.findDepartmentById(departmentId));
    }

    @Transactional
    public DepartmentResource removePersonal(UUID departmentId, UUID userId) {
        Department department = repository.findDepartmentById(departmentId);
        User user = userRepository.findUserById(userId);
        if (!user.getDepartment().equals(department)) {
            throw new BadRequestException(USER_IS_NOT_EXIST);
        }
        user.setDepartment(null);
        userRepository.save(user);
        return mapper.toResource(department);
    }

    public Set<MinimalUserResource> getPersonals(UUID departmentId) {
        Department department = repository.findDepartmentById(departmentId);
        return userMapper.toResource(userRepository.findUsersByDepartment(department));
    }
}
