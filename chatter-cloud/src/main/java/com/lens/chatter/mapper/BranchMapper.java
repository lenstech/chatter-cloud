package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.resource.BranchResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE , uses = {FirmMapper.class})
public interface BranchMapper extends Converter<BranchDto, Branch, BranchResource> {
}