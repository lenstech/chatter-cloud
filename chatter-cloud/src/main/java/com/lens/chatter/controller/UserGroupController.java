package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.constant.Role;
import com.lens.chatter.model.dto.UserGroupDto;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.service.UserGroupService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */

@RestController
@RequestMapping("/user-group")
@Api(value = "User Group", tags = {"User Group Operations"})
public class UserGroupController extends AbstractController<UserGroup, UUID, UserGroupDto, UserGroupResource> {

    @Autowired
    private UserGroupService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Override
    public void setMinRole() {
        super.minRole = Role.DEPARTMENT_ADMIN;
    }

    @Override
    protected AbstractService<UserGroup, UUID, UserGroupDto, UserGroupResource> getService() {
        return service;
    }
}
