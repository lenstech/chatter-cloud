package com.lens.chatter.mapper;

import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.LoginResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DepartmentMapper.class})
public interface LoginMapper {

    LoginResource toResource(User user);
}
