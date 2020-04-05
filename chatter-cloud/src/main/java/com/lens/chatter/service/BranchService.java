package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.mapper.BranchMapper;
import com.lens.chatter.mapper.DepartmentMapper;
import com.lens.chatter.model.dto.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.resource.BranchResource;
import com.lens.chatter.model.resource.DepartmentResource;
import com.lens.chatter.repository.BranchRepository;
import com.lens.chatter.repository.DepartmentRepository;
import com.lens.chatter.repository.FirmRepository;
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
    private BranchMapper mapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private FirmRepository firmRepository;

    @Override
    public BranchRepository getRepository() {
        return repository;
    }

    @Override
    public Converter<BranchDto, Branch, BranchResource> getConverter() {
        return mapper;
    }

    @Override
    public BranchResource save(BranchDto dto) {
        LOGGER.debug(String.format("Saving the dto [%s].", dto));
        Branch branch = getConverter().toEntity(dto);
        branch.setFirm(firmRepository.findFirmById(dto.getFirmId()));
        return getConverter().toResource(getRepository().save(branch));
    }

    @Transactional
    @Override
    public Branch put(UUID branchId, BranchDto dto) {
        LOGGER.debug(String.format("Request to update the record [%s].", branchId));
        Branch old = getRepository().findById(branchId).orElseThrow(() -> new BadRequestException(ID_IS_NOT_EXIST));
        if (dto == null) {
            LOGGER.error(DTO_CANNOT_BE_EMPTY);
            throw new BadRequestException(DTO_CANNOT_BE_EMPTY);
        }
        if (branchId == null) {
            LOGGER.error(ID_CANNOT_BE_EMPTY);
            throw new BadRequestException(ID_CANNOT_BE_EMPTY);
        }
        Branch branch = getConverter().toEntity(dto);
        branch.setId(old.getId());
        branch.setCreatedDate(old.getCreatedDate());
        branch.setFirm(firmRepository.findFirmById(dto.getFirmId()));
        return getRepository().save(branch);
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
}
