package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.UserGroupDto;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MinimalUserMapper.class})
public interface UserGroupMapper extends Converter<UserGroupDto, UserGroup, UserGroupResource> {

}
