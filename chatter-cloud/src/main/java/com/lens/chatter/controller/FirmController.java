package com.lens.chatter.controller;

import com.lens.chatter.common.AbstractController;
import com.lens.chatter.common.AbstractService;
import com.lens.chatter.constant.Role;
import com.lens.chatter.exception.UnauthorizedException;
import com.lens.chatter.model.dto.FirmDto;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.resource.FirmResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.FirmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lens.chatter.constant.ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION;

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

    @Override
    protected AbstractService<Firm, UUID, FirmDto, FirmResource> getService() {
        return service;
    }

    @Autowired
    private JwtResolver jwtResolver;

    @ApiOperation(value = "Get all Branches of a Firm , it can be seen by only Admin", response = FirmResource.class)
    @GetMapping("/get-branches")
    public ResponseEntity getBranchesOfFirm(@RequestHeader("Authorization") String token,
                                            @RequestParam UUID firmId) {
        Role role = jwtResolver.getRoleFromToken(token);
        if (!role.equals(Role.ADMIN)) {
            throw new UnauthorizedException(NOT_AUTHORIZED_FOR_OPERATION);
        }
        return ResponseEntity.ok(service.getBranches(firmId));
    }


}
