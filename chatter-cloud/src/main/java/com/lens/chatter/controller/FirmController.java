package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.organization.FirmDto;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.resource.organization.BranchResource;
import com.lens.chatter.model.resource.organization.FirmResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.service.FirmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 1 Mar 2020
 */

@RestController
@RequestMapping("/firm")
@Api(value = "Firm", tags = {"Firm Operations"})
public class FirmController extends AbstractController<Firm, UUID, FirmDto, FirmResource> {

    @Autowired
    private FirmService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Override
    protected AbstractService<Firm, UUID, FirmDto, FirmResource> getService() {
        return service;
    }

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

    @ApiOperation(value = "Get all Branches of a Firm, it can be seen by basic user", response = BranchResource.class, responseContainer = "List")
    @GetMapping("/get-branches")
    public ResponseEntity getBranchesOfFirm(@RequestHeader("Authorization") String token, @RequestParam UUID firmId) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getBranches(firmId));
    }

    @ApiOperation(value = "Get all Users of a Firm page by page, it can be seen by basic user", responseContainer = "List")
    @GetMapping("/get-users/{page}")
    public Page<MinimalUserResource> getPersonalsOfFirmByPage(@RequestHeader("Authorization") String token,
                                         @RequestParam UUID firmId,
                                         @PathVariable("page") int pageNo,
                                         @RequestParam(required = false) String sortBy,
                                         @RequestParam(required = false) Boolean desc) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return service.getPersonalsOfFirm(firmId, pageNo, sortBy, desc);
    }

    @ApiOperation(value = "Get all Users of a Firm, it can be seen by basic user", response = MinimalUserResource.class, responseContainer = "List")
    @GetMapping("/get-users")
    public ResponseEntity getPersonalsOfFirm(@RequestHeader("Authorization") String token,
                                         @RequestParam UUID firmId) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getPersonalsOfFirm(firmId));
    }

}
