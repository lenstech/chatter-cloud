package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.product.DefectDto;
import com.lens.chatter.model.entity.Defect;
import com.lens.chatter.model.resource.product.DefectResource;
import com.lens.chatter.service.DefectService;
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
@RequestMapping("/defect")
@Api(value = "Defect", tags = {"Defect Operations"})
public class DefectController extends AbstractController<Defect, UUID, DefectDto, DefectResource> {

    @Autowired
    private DefectService service;

    @Override
    protected AbstractService<Defect, UUID, DefectDto, DefectResource> getService() {
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
