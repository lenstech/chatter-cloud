package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.organization.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.resource.organization.BranchResource;
import com.lens.chatter.model.resource.organization.DepartmentResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */

@RestController
@RequestMapping("/branch")
@Api(value = "Branch", tags = {"Branch Operations"})
public class BranchController extends AbstractController<Branch, UUID, BranchDto, BranchResource> {

    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);
    @Autowired
    private BranchService service;
    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Override
    protected AbstractService<Branch, UUID, BranchDto, BranchResource> getService() {
        return service;
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
        return Role.BRANCH_ADMIN;
    }

    @Override
    public Role getDeleteRole() {
        return Role.FIRM_ADMIN;
    }


    @ApiOperation(value = "Get all Departments of a Branch , it can be seen by only Admin", response = DepartmentResource.class, responseContainer = "Set")
    @GetMapping("/get-departments")
    public ResponseEntity<Set<DepartmentResource>> getDepartmentsOfBranch(@RequestHeader("Authorization") String token,
                                                                          @RequestParam UUID branchId) {
        logger.info(String.format("Requesting getDepartmentsOfBranch branchId: %s.", branchId));
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getDepartments(branchId));
    }

    @ApiOperation(value = "Add Department to a Branch , it can be done by only Admin", response = BranchResource.class)
    @PutMapping("/add-department")
    public ResponseEntity<BranchResource> addDepartmentToBranch(@RequestHeader("Authorization") String token,
                                                                @RequestParam UUID departmentId,
                                                                @RequestParam UUID branchId) {
        logger.info(String.format("Requesting addDepartmentToBranch branchId: %s , departmentId: %s", branchId, departmentId));
        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        return ResponseEntity.ok(service.addDepartment(branchId, departmentId));
    }

    @ApiOperation(value = "Remove Department from Branch, it can be done by only Admin", response = BranchResource.class)
    @PutMapping("/remove-department")
    public ResponseEntity<BranchResource> removeDepartmentFromBranch(@RequestHeader("Authorization") String token,
                                                                     @RequestParam UUID departmentId,
                                                                     @RequestParam UUID branchId) {
        logger.info(String.format("Requesting removeDepartmentFromBranch branchId: %s , departmentId: %s", branchId, departmentId));
        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        return ResponseEntity.ok(service.removeDepartment(branchId, departmentId));
    }

    @ApiOperation(value = "Get all Users of a Branch page by page, it can be seen by basic user", responseContainer = "List")
    @GetMapping("/get-users/{page}")
    public Page<MinimalUserResource> getPersonalsOfBranch(@RequestHeader("Authorization") String token,
                                                          @RequestParam UUID branchId,
                                                          @PathVariable("page") int pageNo,
                                                          @RequestParam(required = false) String sortBy,
                                                          @RequestParam(required = false) Boolean desc) {
        logger.info(String.format("Requesting getPersonalsOfBranch pageByPage branchId: %s ", branchId));
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return service.getPersonalsOfBranch(branchId, pageNo, sortBy, desc);
    }

    @ApiOperation(value = "Get all Users of a Branch page by page, it can be seen by basic user", response = MinimalUserResource.class, responseContainer = "List")
    @GetMapping("/get-users")
    public ResponseEntity<List<MinimalUserResource>> getPersonalsOfBranch(@RequestHeader("Authorization") String token,
                                                                          @RequestParam UUID branchId) {
        logger.info(String.format("Requesting getPersonalsOfBranch branchId: %s ", branchId));
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getPersonalsOfBranch(branchId));
    }

    @ApiOperation(value = "Get shift quantity of a Branch", response = Integer.class)
    @GetMapping("/get-daily-shift-quantity")
    public ResponseEntity<Integer> getDailyShiftQuantity(@RequestHeader("Authorization") String token,
                                                         @RequestParam UUID branchId) {
        logger.info(String.format("Requesting getDailyShiftQuantity branchId: %s ", branchId));
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.getDailyShiftQuantity(branchId));
    }
}
