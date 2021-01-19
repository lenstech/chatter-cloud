package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.ProductDto;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.resource.product.ProductResource;
import com.lens.chatter.service.BranchService;
import com.lens.chatter.service.ProductTypeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DefectMapper.class, ProductTypeMapper.class, ProductTypeService.class, BranchService.class, BranchMapper.class})
public interface ProductMapper extends Converter<ProductDto, Product, ProductResource> {

    @Override
    @Mapping(source = "productTypeId", target = "productType", qualifiedByName = "fromIdToEntity")
    @Mapping(source = "branchId", target = "branch", qualifiedByName = "fromIdToEntity")
//    @Mapping(source = "defectIds", target = "defects", qualifiedByName = "fromIdsToEntities")
    Product toEntity(ProductDto productDto);

    @Override
    @Mapping(source = "productTypeId", target = "productType", qualifiedByName = "fromIdToEntity")
    @Mapping(source = "branchId", target = "branch", qualifiedByName = "fromIdToEntity")
    void toEntityForUpdate(ProductDto productDto, @MappingTarget Product product);
}
