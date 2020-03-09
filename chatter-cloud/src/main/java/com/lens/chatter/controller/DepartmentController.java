package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.constant.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.model.dto.DepartmentDto;
import com.lens.chatter.model.entity.Department;
import com.lens.chatter.model.resource.DepartmentResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;

/**
 * Created by Emir Gökdemir
 * on 23 Şub 2020
 */

@RestController
@RequestMapping("/department")
@Api(value = "Department", tags = {"Department Operations"})
public class DepartmentController extends AbstractController<Department, UUID, DepartmentDto, DepartmentResource> {

    @Override
    protected AbstractService<Department, UUID, DepartmentDto, DepartmentResource> getService() {
        return service;
    }

    @Autowired
    private DepartmentService service;

    @Autowired
    private JwtResolver jwtResolver;

    @ApiOperation(value = "Get all Personal of a Department , it can be seen by only Admin", response = DepartmentResource.class)
    @GetMapping("/get-personals")
    public ResponseEntity getPersonalsOfDepartment(@RequestHeader("Authorization") String token,
                                                   @RequestParam UUID departmentId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(service.getPersonals(departmentId));
    }

    @ApiOperation(value = "Add Personal to a Department , it can be done by only Admin", response = MinimalUserResource.class, responseContainer = "Set")
    @PutMapping("/add-personal")
    public ResponseEntity addPersonalToDepartment(@RequestHeader("Authorization") String token,
                                                  @RequestParam UUID personalUserId,
                                                  @RequestParam UUID departmentId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(service.addPersonal(departmentId, personalUserId));
    }

    @ApiOperation(value = "Remove Personal from a Department , it can be done by only Admin", response = DepartmentResource.class)
    @PutMapping("/remove-personal")
    public ResponseEntity removePersonalToDepartment(@RequestHeader("Authorization") String token,
                                                     @RequestParam UUID personalUserId,
                                                     @RequestParam UUID departmentId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(service.removePersonal(departmentId, personalUserId));
    }

}
