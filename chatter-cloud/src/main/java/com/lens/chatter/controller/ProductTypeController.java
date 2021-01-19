package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.product.ProductTypeDto;
import com.lens.chatter.model.entity.ProductType;
import com.lens.chatter.model.resource.product.ProductTypeResource;
import com.lens.chatter.service.ProductTypeService;
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
@RequestMapping("/product-type")
@Api(value = "Product Type", tags = {"Product Type Operations"})
public class ProductTypeController extends AbstractController<ProductType, UUID, ProductTypeDto, ProductTypeResource> {

    @Autowired
    private ProductTypeService service;

    @Override
    protected AbstractService<ProductType, UUID, ProductTypeDto, ProductTypeResource> getService() {
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
