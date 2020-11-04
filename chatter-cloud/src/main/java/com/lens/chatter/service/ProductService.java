package com.lens.chatter.service;

import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.Converter;
import com.lens.chatter.mapper.ProductMapper;
import com.lens.chatter.model.dto.product.ProductDto;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.resource.product.ProductResource;
import com.lens.chatter.repository.ChatterRepository;
import com.lens.chatter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@Service
public class ProductService extends AbstractService<Product, UUID, ProductDto, ProductResource> {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public ChatterRepository<Product, UUID> getRepository() {
        return repository;
    }

    @Override
    public Converter<ProductDto, Product, ProductResource> getConverter() {
        return mapper;
    }

}
