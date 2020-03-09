package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.mapper.BranchMapper;
import com.lens.chatter.mapper.FirmMapper;
import com.lens.chatter.model.dto.FirmDto;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.resource.BranchResource;
import com.lens.chatter.model.resource.FirmResource;
import com.lens.chatter.repository.BranchRepository;
import com.lens.chatter.repository.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
    private FirmMapper mapper;

    @Autowired
    private BranchMapper branchMapper;

    @Override
    public JpaRepository<Firm, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<FirmDto, Firm, FirmResource> getConverter() {
        return mapper;
    }

    public Set<BranchResource> getBranches(UUID firmId) {
        return branchMapper.toResources(branchRepository.findBranchesByFirm(repository.findFirmById(firmId)));
    }
}
