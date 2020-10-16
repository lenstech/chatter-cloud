package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.organization.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.resource.organization.BranchResource;
import com.lens.chatter.model.resource.organization.DepartmentResource;
import com.lens.chatter.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */

@RestController
@RequestMapping("/branch")
@Api(value = "Branch", tags = {"Branch Operations"})
public class BranchController extends AbstractController<Branch, UUID, BranchDto, BranchResource> {

    @Autowired
    private BranchService branchService;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Override
    protected AbstractService<Branch, UUID, BranchDto, BranchResource> getService() {
        return branchService;
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


    @ApiOperation(value = "Get all Departments of a Branch , it can be seen by only Admin", response = DepartmentResource.class, responseContainer = "Set")
    @GetMapping("/get-departments")
    public ResponseEntity getDepartmentsOfBranch(@RequestHeader("Authorization") String token,
                                                 @RequestParam UUID branchId) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(branchService.getDepartments(branchId));
    }

    @ApiOperation(value = "Add Department to a Branch , it can be done by only Admin", response = DepartmentResource.class)
    @PutMapping("/add-department")
    public ResponseEntity addDepartmentToBranch(@RequestHeader("Authorization") String token,
                                                @RequestParam UUID departmentId,
                                                @RequestParam UUID branchId) {
        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        return ResponseEntity.ok(branchService.addDepartment(branchId, departmentId));
    }

    @ApiOperation(value = "Remove Department from Branch, it can be done by only Admin", response = DepartmentResource.class)
    @PutMapping("/remove-department")
    public ResponseEntity removeDepartmentFromBranch(@RequestHeader("Authorization") String token,
                                                     @RequestParam UUID departmentId,
                                                     @RequestParam UUID branchId) {

        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        return ResponseEntity.ok(branchService.removeDepartment(branchId, departmentId));
    }
}
