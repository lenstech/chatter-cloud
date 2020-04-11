package com.lens.chatter.common;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

import static com.lens.chatter.constant.HttpSuccessMessagesConstants.SUCCESSFULLY_DELETED;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */
@Component
public abstract class AbstractController<T extends AbstractEntity, ID extends Serializable, DTO, RES> {

    protected abstract AbstractService<T, ID, DTO, RES> getService();

    @Autowired
    private AuthorizationConfig authorizationConfig;

    public Role getMinRole() {
        return minRole;
    }

    public abstract void setMinRole();

    public Role minRole;

    @ApiOperation(value = "Create Object, it can be done by authorization")
    @PostMapping
    public RES save(@RequestHeader("Authorization") String token, @RequestBody DTO dto) {
        setMinRole();
        authorizationConfig.permissionCheck(token, minRole);
        return getService().save(dto);
    }

    @ApiOperation(value = "Get Object")
    @GetMapping
    public RES get(@RequestHeader("Authorization") String token, @RequestParam ID objectId) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return getService().get(objectId);
    }

    @ApiOperation(value = "Get All Object", responseContainer = "List")
    @GetMapping("/all")
    public List<RES> getAll(@RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return getService().getAll();
    }

    @ApiOperation(value = "Update Object, it can be done by authorization")
    @PutMapping
    public RES update(@RequestHeader("Authorization") String token,
                                 @RequestBody DTO dto,
                                 @RequestParam ID objectId) {
        setMinRole();
        authorizationConfig.permissionCheck(token, minRole);
        return getService().put(objectId, dto);
    }

    @ApiOperation(value = "Delete Object,  it can be done by authorization", response = void.class)
    @DeleteMapping
    public String delete(@RequestHeader("Authorization") String token,
                                 @RequestParam ID objectId) {
        setMinRole();
        authorizationConfig.permissionCheck(token, minRole);
        getService().delete(objectId);
        return SUCCESSFULLY_DELETED;
    }
}
