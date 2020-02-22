package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MinimalUserMapper{
    MinimalUserResource toResource(User user);
}
