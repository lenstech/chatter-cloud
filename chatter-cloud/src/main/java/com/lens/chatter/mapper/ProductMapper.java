package com.lens.chatter.mapper;

import com.lens.chatter.common.Converter;
import com.lens.chatter.model.dto.product.ProductDto;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.resource.product.ProductResource;
import com.lens.chatter.repository.ProductTypeRepository;
import com.lens.chatter.service.BranchService;
import com.lens.chatter.utils.MapperUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {DefectMapper.class, MapperUtils.class, ProductTypeMapper.class, ProductTypeRepository.class, BranchService.class, BranchMapper.class})
public interface ProductMapper extends Converter<ProductDto, Product, ProductResource> {

    @Override
    @Mapping(source = "productTypeId", target = "productType", qualifiedByName = "findOneById")
    @Mapping(source = "branchId", target = "branch", qualifiedByName = "findOneById")
    @Mapping(source = "defectIds", target = "defects", qualifiedByName = "toDefect")
    Product toEntity(ProductDto productDto);
}
