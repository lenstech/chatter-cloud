package com.lens.chatter.common;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.constant.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.security.JwtResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

import static com.lens.chatter.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;
import static com.lens.chatter.constant.HttpSuccesMessagesConstants.SUCCESSFULLY_DELETED;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */
@Component
public abstract class AbstractController<T extends AbstractEntity, ID extends Serializable, DTO, RES> {

    protected abstract AbstractService<T, ID, DTO, RES> getService();

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private AuthorizationConfig authorizationConfig;
//
//    public Role getMinRole() {
//        return minRole;
//    }
//
//    public abstract void setMinRole();
//
//    public Role minRole;

    @ApiOperation(value = "Create Object")
    @PostMapping("/create")
    public ResponseEntity save(@RequestHeader("Authorization") String token, @RequestBody DTO dto) {
        authorizationConfig.permissionCheck(token,Role.ADMIN);
        return ResponseEntity.ok(getService().save(dto));
    }

    @ApiOperation(value = "Get Object")
    @GetMapping("/get")
    public ResponseEntity get(@RequestHeader("Authorization") String token, @RequestParam ID objectId) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(getService().get(objectId));
    }

    @ApiOperation(value = "Get All Object", responseContainer = "List")
    @GetMapping("/get-all")
    public ResponseEntity getAll(@RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(getService().getAll());
    }

    @ApiOperation(value = "Update Object, it can be done by at least Branch Admin")
    @PutMapping("/update")
    public ResponseEntity update(@RequestHeader("Authorization") String token,
                                 @RequestBody DTO dto,
                                 @RequestParam ID objectId) {
        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        return ResponseEntity.ok(getService().put(objectId, dto));
    }

    @ApiOperation(value = "Delete Object, it can be done by only Admin", response = void.class)
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestHeader("Authorization") String token,
                                 @RequestParam ID objectId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        getService().delete(objectId);
        return ResponseEntity.ok(SUCCESSFULLY_DELETED);
    }
}
