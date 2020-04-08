package com.lens.chatter.mapper;


import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.organization.FirmDto;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.resource.organization.FirmResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FirmMapper extends Converter<FirmDto, Firm, FirmResource> {
}
