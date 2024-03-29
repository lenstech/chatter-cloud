package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.enums.ChannelType;
import com.lens.chatter.mapper.BranchMapper;
import com.lens.chatter.mapper.FirmMapper;
import com.lens.chatter.mapper.MinimalUserMapper;
import com.lens.chatter.model.dto.organization.FirmDto;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.resource.organization.BranchResource;
import com.lens.chatter.model.resource.organization.FirmResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.repository.BranchRepository;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.FirmRepository;
import com.lens.chatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 1 Mar 2020
 */
@Service
public class FirmService extends AbstractService<Firm, UUID, FirmDto, FirmResource> {

    @Autowired
    private FirmRepository repository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinimalUserMapper minimalUserMapper;

    @Autowired
    private FirmMapper mapper;

    @Autowired
    private BranchMapper branchMapper;

    @Autowired
    private CreateMessageGroupService createMessageGroupService;

    @Override
    public ChatterRepository<Firm, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<FirmDto, Firm, FirmResource> getConverter() {
        return mapper;
    }

    public Set<BranchResource> getBranches(UUID firmId) {
        return branchMapper.toResources(branchRepository.findBranchesByFirmId(firmId));
    }

    public Page<MinimalUserResource> getPersonalsOfFirm(UUID firmId, int pageNumber, String sortBy, Boolean desc) {
        PageRequest pageable = getPageable(pageNumber, sortBy, desc);
        return userRepository.findUsersByDepartmentBranchFirmId(pageable, firmId).map(minimalUserMapper::toResource);
    }

    public List<MinimalUserResource> getPersonalsOfFirm(UUID branchId) {
        return minimalUserMapper.toResources(userRepository.findUsersByDepartmentBranchFirmId(branchId));
    }

    @Override
    protected Firm afterSaveOperations(Firm entity) {
        createMessageGroupService.saveFirebaseChannel(entity.getId(), entity.getId(), entity.getName(), ChannelType.FIRM);
        return entity;
    }
}
