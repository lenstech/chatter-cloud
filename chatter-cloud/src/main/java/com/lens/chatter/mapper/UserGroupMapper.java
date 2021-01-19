package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.user.UserGroupDto;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.service.UserService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MinimalUserMapper.class, UserService.class})
public interface UserGroupMapper extends Converter<UserGroupDto, UserGroup, UserGroupResource> {


    @Override
    @Mapping(source = "managerId", target = "manager", qualifiedByName = "fromIdToEntity",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserGroup toEntity(UserGroupDto userGroupDto);

    @Override
    @Mapping(source = "managerId", target = "manager", qualifiedByName = "fromIdToEntity",
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntityForUpdate(UserGroupDto userGroupDto, @MappingTarget UserGroup userGroup);
}
