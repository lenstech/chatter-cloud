package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.product.ProductDto;
import com.lens.chatter.model.entity.Product;
import com.lens.chatter.model.resource.product.ProductResource;
import com.lens.chatter.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Nis 2020
 */
@RestController
@RequestMapping("/product")
@Api(value = "Product", tags = {"Product Operations -> ProductTimeExample(ISO_DATE_TIME): 2021-01-26T14:08:58.899Z"})
public class ProductController extends AbstractController<Product, UUID, ProductDto, ProductResource> {

    @Autowired
    private ProductService service;

    @Override
    protected AbstractService<Product, UUID, ProductDto, ProductResource> getService() {
        return service;
    }

    @Override
    public Role getSaveRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getGetRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getGetAllRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getUpdateRole() {
        return Role.BASIC_USER;
    }

    @Override
    public Role getDeleteRole() {
        return Role.BASIC_USER;
    }

}
