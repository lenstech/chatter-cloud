package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.common.ListOfIdDto;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.user.UserGroupDto;
import com.lens.chatter.model.entity.UserGroup;
import com.lens.chatter.model.resource.user.UserGroupResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.UserGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private JwtResolver jwtResolver;

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Override
    public void setSaveRole() {
        super.saveRole = Role.BASIC_USER;
    }

    @Override
    public void setGetRole() {
        super.getRole = Role.BASIC_USER;
    }

    @Override
    public void setGetAllRole() {
        super.getAllRole = Role.BASIC_USER;
    }

    @Override
    public void setUpdateRole() {
        super.updateRole = Role.BASIC_USER;
    }

    @Override
    public void setDeleteRole() {
        super.deleteRole = Role.BASIC_USER;
    }

    @Override
    public void setEntityName() {
        entityName = "UserGroup";
    }

    @Override
    protected AbstractService<UserGroup, UUID, UserGroupDto, UserGroupResource> getService() {
        return service;
    }

    @ApiOperation(value = "Add users to an user group, with  group id, user ", response = UserGroupResource.class)
    @PutMapping("/add-users")
    public ResponseEntity<UserGroupResource> addUsersToGroup(@RequestHeader("Authorization") String token, @RequestParam UUID groupId, @RequestBody ListOfIdDto userIds) {
        logger.info(String.format("Requesting addUsersToGroup with groupId: %s ", groupId));
        authorizationConfig.permissionCheck(token, Role.DEPARTMENT_ADMIN);
        return ResponseEntity.ok(service.addUsers(groupId, userIds.getIds()));
    }

    @ApiOperation(value = "Remove users from an user group, with  group id, user ", response = UserGroupResource.class)
    @PutMapping("/remove-users")
    public ResponseEntity<UserGroupResource> removeUsersFromGroup(@RequestHeader("Authorization") String token, @RequestParam UUID groupId, @RequestBody ListOfIdDto userIds) {
        logger.info(String.format("Requesting removeUsersFromGroup with groupId: %s ", groupId));
        authorizationConfig.permissionCheck(token, Role.DEPARTMENT_ADMIN);
        return ResponseEntity.ok(service.removeUsers(groupId, userIds.getIds()));
    }

    @ApiOperation(value = "Get the user's  userGroups, with his/her token ", response = UserGroupResource.class, responseContainer = "List")
    @GetMapping("/my-groups")
    public ResponseEntity<List<UserGroupResource>> getMyUserGroups(@RequestHeader("Authorization") String token) {
        UUID userId = jwtResolver.getIdFromToken(token);
        logger.info(String.format("Requesting getMyUserGroups with userId: %s ", userId));
        return ResponseEntity.ok(service.getMyUserGroups(userId));
    }
}
