package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.user.UserGroupDto;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.service.UserGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @ApiOperation(value = "Add users to an user group, with  group id, user ", response = UserGroupResource.class)
    @PutMapping("/add-users")
    public ResponseEntity addUsersToGroup(@RequestHeader("Authorization") String token, @RequestParam UUID groupId, @RequestBody List<UUID> userIds) {
        authorizationConfig.permissionCheck(token, Role.DEPARTMENT_ADMIN);
        return ResponseEntity.ok(service.addUsers(groupId, userIds));
    }

    @ApiOperation(value = "Remove users from an user group, with  group id, user ", response = UserGroupResource.class)
    @PutMapping("/remove-users")
    public ResponseEntity removeUsersToGroup(@RequestHeader("Authorization") String token, @RequestParam UUID groupId, @RequestBody List<UUID> userIds) {
        authorizationConfig.permissionCheck(token, Role.DEPARTMENT_ADMIN);
        return ResponseEntity.ok(service.removeUsers(groupId, userIds));
    }
}
