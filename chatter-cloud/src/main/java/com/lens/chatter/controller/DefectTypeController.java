package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.product.DefectTypeDto;
import com.lens.chatter.model.entity.DefectType;
import com.lens.chatter.model.resource.product.DefectTypeResource;
import com.lens.chatter.service.DefectTypeService;
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
@RequestMapping("/defect-type")
@Api(value = "Defect Type", tags = {"Defect Type Operations"})
public class DefectTypeController extends AbstractController<DefectType, UUID, DefectTypeDto, DefectTypeResource> {

    @Autowired
    private DefectTypeService service;

    @Override
    protected AbstractService<DefectType, UUID, DefectTypeDto, DefectTypeResource> getService() {
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
