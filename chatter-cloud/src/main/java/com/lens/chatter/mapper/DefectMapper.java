package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.DefectDto;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.resource.product.DefectResource;
import com.lens.chatter.repository.DefectTypeRepository;
import com.lens.chatter.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {DefectTypeMapper.class, DefectTypeRepository.class, ProductRepository.class})
public interface DefectMapper extends Converter<DefectDto, Defect, DefectResource> {

    @Override
    @Mapping(source = "defectTypeId", target = "defectType", qualifiedByName = "findOneById")
    @Mapping(source = "productId", target = "product", qualifiedByName = "findOneById")
    Defect toEntity(DefectDto defectDto);
}
