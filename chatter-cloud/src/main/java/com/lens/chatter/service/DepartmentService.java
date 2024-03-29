package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.enums.ChannelType;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.DepartmentMapper;
import com.lens.chatter.mapper.MinimalUserMapper;
import com.lens.chatter.model.dto.organization.DepartmentDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.organization.DepartmentResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.repository.BranchRepository;
import com.lens.chatter.repository.DepartmentRepository;
import com.lens.chatter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.*;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */
@Service
public class DepartmentService extends AbstractService<Department, UUID, DepartmentDto, DepartmentResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinimalUserMapper userMapper;

    @Autowired
    private CreateMessageGroupService createMessageGroupService;

    @Override
    public DepartmentRepository getRepository() {
        return repository;
    }

    @Override
    public Converter<DepartmentDto, Department, DepartmentResource> getConverter() {
        return mapper;
    }

    @Override
    public DepartmentResource save(DepartmentDto dto, UUID userId) {
        LOGGER.debug(String.format("Saving the dto [%s].", dto));
        Department department = getConverter().toEntity(dto);
        department.setBranch(branchRepository.findBranchById(dto.getBranchId()));
        return getConverter().toResource(getRepository().save(department));
    }

    @Transactional
    @Override
    public DepartmentResource put(UUID departmentId, DepartmentDto dto, UUID userId) {
        LOGGER.debug(String.format("Request to update the record [%s].", departmentId));
        Department old = getRepository().findById(departmentId).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        if (dto == null) {
            LOGGER.error(DTO_CANNOT_BE_EMPTY);
            throw new BadRequestException(DTO_CANNOT_BE_EMPTY);
        }
        if (departmentId == null) {
            LOGGER.error(ID_CANNOT_BE_EMPTY);
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        Department department = getConverter().toEntity(dto);
        department.setId(old.getId());
        department.setCreatedDate(old.getCreatedDate());
        department.setBranch(branchRepository.findBranchById(dto.getBranchId()));
        return mapper.toResource(getRepository().save(department));
    }

    @Transactional
    public DepartmentResource addPersonal(UUID departmentId, UUID userId) {
        Department department = repository.findDepartmentById(departmentId);
        User user = userService.fromIdToEntity(userId);
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
        User user = userService.fromIdToEntity(userId);
        if (!user.getDepartment().equals(department)) {
            throw new BadRequestException(USER_IS_NOT_EXIST);
        }
        user.setDepartment(null);
        userRepository.save(user);
        return mapper.toResource(department);
    }

    public Set<MinimalUserResource> getPersonals(UUID departmentId) {
        Department department = repository.findDepartmentById(departmentId);
        return userMapper.toResources(userRepository.findUsersByDepartment(department));
    }

    @Override
    protected Department afterSaveOperations(Department entity) {
        createMessageGroupService.saveFirebaseChannel(entity.getId(), entity.getBranch().getFirm().getId(), entity.getName(), ChannelType.DEPARTMENT);
        return entity;
    }
}
