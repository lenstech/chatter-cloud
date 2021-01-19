package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.DefectDto;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.resource.product.DefectResource;
import com.lens.chatter.service.DefectTypeService;
import com.lens.chatter.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DefectTypeMapper.class, DefectTypeService.class, ProductService.class})
public interface DefectMapper extends Converter<DefectDto, Defect, DefectResource> {

    @Override
    @Mapping(source = "defectTypeId", target = "defectType", qualifiedByName = "fromIdToEntity")
    @Mapping(source = "productId", target = "product", qualifiedByName = "fromIdToEntity")
    Defect toEntity(DefectDto defectDto);

    @Override
    @Mapping(source = "defectTypeId", target = "defectType", qualifiedByName = "fromIdToEntity")
    @Mapping(source = "productId", target = "product", qualifiedByName = "fromIdToEntity")
    void toEntityForUpdate(DefectDto defectDto, @MappingTarget Defect defect);
}
