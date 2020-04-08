package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.DefectTypeDto;
import com.lens.chatter.model.entity.DefectType;
import com.lens.chatter.model.resource.product.DefectTypeResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DefectTypeMapper extends Converter<DefectTypeDto, DefectType, DefectTypeResource> {
}
