package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.DepartmentDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.resource.DepartmentResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {BranchMapper.class})
public interface DepartmentMapper extends Converter<DepartmentDto, Department, DepartmentResource> {
}
