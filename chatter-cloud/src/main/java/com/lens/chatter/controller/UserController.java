package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.entity.User;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 23 Ağu 2020
 */

@RestController
@RequestMapping("/user")
@Api(value = "User", tags = {"User Operations"})
public class UserController extends AbstractController<User, UUID, RegisterDto, MinimalUserResource> {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Override
    protected AbstractService<User, UUID, RegisterDto, MinimalUserResource> getService() {
        return userService;
    }

    @Override
    public Role getSaveRole() {
        return Role.FIRM_ADMIN;
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
        return Role.FIRM_ADMIN;
    }

    @Override
    public Role getDeleteRole() {
        return Role.FIRM_ADMIN;
    }

}

