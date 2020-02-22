package com.lens.chatter.controller;

import com.lens.chatter.model.dto.LoginDto;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value = "Login", tags = {"Login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "Login with the username (email) and password", response = LoginResource.class)
    @PostMapping("")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        try {
            return ResponseEntity.ok(loginService.login(loginDto));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
