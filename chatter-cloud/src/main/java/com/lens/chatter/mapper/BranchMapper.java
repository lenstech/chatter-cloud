package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.organization.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.resource.organization.BranchResource;
import com.lens.chatter.service.FirmService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {FirmMapper.class, FirmService.class})
public interface BranchMapper extends Converter<BranchDto, Branch, BranchResource> {

    @Override
    @Mapping(source = "firmId", target = "firm", qualifiedByName = "fromIdToEntity")
    Branch toEntity(BranchDto branchDto);

    @Override
    @Mapping(source = "firmId", target = "firm", qualifiedByName = "fromIdToEntity")
    void toEntityForUpdate(BranchDto branchDto, @MappingTarget Branch branch);
}
