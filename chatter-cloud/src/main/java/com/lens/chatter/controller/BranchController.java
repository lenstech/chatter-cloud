package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.constant.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.model.dto.BranchDto;
import com.lens.chatter.model.entity.Branch;
import com.lens.chatter.model.resource.BranchResource;
import com.lens.chatter.model.resource.DepartmentResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;

/**
 * Created by Emir Gökdemir
 * on 29 Şub 2020
 */

@RestController
@RequestMapping("/branch")
@Api(value = "Branch", tags = {"Branch Operations"})
public class BranchController extends AbstractController<Branch, UUID, BranchDto, BranchResource> {

    @Override
    protected AbstractService<Branch, UUID, BranchDto, BranchResource> getService() {
        return branchService;
    }
//
//    @Override
//    public void setMinRole() {
//        super.minRole=Role.ADMIN;
//    }

    @Autowired
    private BranchService branchService;

    @Autowired
    private JwtResolver jwtResolver;

    @ApiOperation(value = "Get all Departments of a Branch , it can be seen by only Admin", response = DepartmentResource.class, responseContainer = "Set")
    @GetMapping("/get-departments")
    public ResponseEntity getDepartmentsOfDepartment(@RequestHeader("Authorization") String token,
                                                     @RequestParam UUID branchId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(branchService.getDepartments(branchId));
    }

    @ApiOperation(value = "Add Department to a Branch , it can be done by only Admin", response = DepartmentResource.class)
    @PutMapping("/add-department")
    public ResponseEntity addPersonalToDepartment(@RequestHeader("Authorization") String token,
                                                  @RequestParam UUID departmentId,
                                                  @RequestParam UUID branchId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(branchService.addDepartment(branchId, departmentId));
    }

    @ApiOperation(value = "Remove Department from Branch, it can be done by only Admin", response = DepartmentResource.class)
    @PutMapping("/remove-department")
    public ResponseEntity removePersonalToDepartment(@RequestHeader("Authorization") String token,
                                                     @RequestParam UUID departmentId,
                                                     @RequestParam UUID branchId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(branchService.removeDepartment(branchId, departmentId));
    }

}
