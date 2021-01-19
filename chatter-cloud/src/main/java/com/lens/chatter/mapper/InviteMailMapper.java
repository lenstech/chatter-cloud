package com.lens.chatter.mapper;

import com.lens.chatter.model.dto.user.InviteMailDto;
import com.lens.chatter.model.resource.user.InviteMailResource;
import com.lens.chatter.service.DepartmentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DepartmentService.class})
public interface InviteMailMapper {

    @Mapping(source = "departmentId", target = "department", qualifiedByName = "get")
    InviteMailResource DtoToResource(InviteMailDto inviteMailDto);
}
