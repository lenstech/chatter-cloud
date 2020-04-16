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
@Api(value = "Product", tags = {"Product Operations"})
public class ProductController extends AbstractController<Product, UUID, ProductDto, ProductResource> {

    @Autowired
    private ProductService service;

    @Override
    protected AbstractService<Product, UUID, ProductDto, ProductResource> getService() {
        return service;
    }

    @Override
    public void setMinRole() {
        super.minRole = Role.DEPARTMENT_ADMIN;
    }
}
