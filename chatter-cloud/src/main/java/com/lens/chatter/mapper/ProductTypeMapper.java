package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.ProductTypeDto;
import com.lens.chatter.model.entity.ProductType;
import com.lens.chatter.model.resource.product.ProductTypeResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductTypeMapper extends Converter<ProductTypeDto, ProductType, ProductTypeResource> {
}
