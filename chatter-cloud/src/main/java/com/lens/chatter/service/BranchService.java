package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.enums.ChannelType;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.BranchMapper;
import com.lens.chatter.mapper.DepartmentMapper;
import com.lens.chatter.mapper.MinimalUserMapper;
import com.lens.chatter.model.dto.organization.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.resource.organization.BranchResource;
import com.lens.chatter.model.resource.organization.DepartmentResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.repository.BranchRepository;
import com.lens.chatter.repository.DepartmentRepository;
import com.lens.chatter.repository.FirmRepository;
import com.lens.chatter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.*;
import static com.lens.chatter.constant.GeneralConstants.PAGE_SIZE;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */
@Service
public class BranchService extends AbstractService<Branch, UUID, BranchDto, BranchResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchService.class);

    @Autowired
    private BranchRepository repository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinimalUserMapper minimalUserMapper;

    @Autowired
    private BranchMapper mapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private FirmRepository firmRepository;

    @Autowired
    private CreateMessageGroupService createMessageGroupService;

    @Override
    public BranchRepository getRepository() {
        return repository;
    }

    @Override
    public Converter<BranchDto, Branch, BranchResource> getConverter() {
        return mapper;
    }

    @Transactional
    public BranchResource addDepartment(UUID departmentId, UUID branchId) {
        Branch branch = repository.findBranchById(branchId);
        Department department = departmentRepository.findDepartmentById(departmentId);
        if (department.getBranch().equals(branch)) {
            throw new BadRequestException(DEPARTMENT_ALREADY_ADDED_TO_BRANCH);
        }
        department.setBranch(branch);
        departmentRepository.save(department);
        return mapper.toResource(branch);
    }

    @Transactional
    public BranchResource removeDepartment(UUID branchId, UUID departmentId) {
        Branch branch = repository.findBranchById(branchId);
        Department department = departmentRepository.findDepartmentById(departmentId);
        if (!department.getBranch().equals(branch)) {
            throw new BadRequestException(DEPARTMENT_IS_NOT_EXIST);
        }
        department.setBranch(null);
        departmentRepository.save(department);
        return mapper.toResource(branch);
    }

    public Set<DepartmentResource> getDepartments(UUID branchId) {
        return departmentMapper.toResources(departmentRepository.findDepartmentsByBranchId(branchId));
    }

    public Page<MinimalUserResource> getPersonalsOfBranch(UUID branchId, int pageNumber, String sortBy, Boolean desc){
        PageRequest pageable;
        if (desc == null) {
            desc = true;
        }
        try {
            if (desc) {
                pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.DESC, sortBy);
            } else {
                pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.Direction.ASC, sortBy);
            }
            return userRepository.findUsersByDepartmentBranchId(pageable, branchId).map(minimalUserMapper::toResource);
        } catch (Exception e) {
            pageable = PageRequest.of(pageNumber, PAGE_SIZE);
            return userRepository.findUsersByDepartmentBranchId(pageable, branchId).map(minimalUserMapper::toResource);
        }
    }

    public List<MinimalUserResource> getPersonalsOfBranch(UUID branchId){
        return minimalUserMapper.toResources(userRepository.findUsersByDepartmentBranchId(branchId));
    }

    public Integer getDailyShiftQuantity(UUID branchId){
        return fromIdToEntity(branchId).getDailyShiftQuantity();
    }

    @Override
    protected Branch afterSaveOperations(Branch entity) {
        createMessageGroupService.saveFirebaseChannel(entity.getId(), entity.getFirm().getId(), entity.getName(), ChannelType.BRANCH);
        return entity;
    }
}
