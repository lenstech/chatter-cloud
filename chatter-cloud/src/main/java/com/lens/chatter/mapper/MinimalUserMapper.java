package com.lens.chatter.mapper;

import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MinimalUserMapper {
    MinimalUserResource toResource(User user);

    Set<MinimalUserResource> toResource(Set<User> users);

    List<MinimalUserResource> toResource(List<User> users);
}
