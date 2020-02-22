package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.LoginDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.LoginResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoginMapper extends Converter<LoginDto, User, LoginResource> {
}
