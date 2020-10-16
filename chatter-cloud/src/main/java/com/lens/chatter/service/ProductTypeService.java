package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.mapper.ProductTypeMapper;
import com.lens.chatter.model.dto.product.ProductTypeDto;
import com.lens.chatter.model.entity.ProductType;
import com.lens.chatter.model.resource.product.ProductTypeResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.ProductTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@Service
public class ProductTypeService extends AbstractService<ProductType, UUID, ProductTypeDto, ProductTypeResource> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTypeService.class);

    @Autowired
    private ProductTypeRepository repository;

    @Autowired
    private ProductTypeMapper mapper;

    @Override
    public ChatterRepository<ProductType, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<ProductTypeDto, ProductType, ProductTypeResource> getConverter() {
        return mapper;
    }


}
