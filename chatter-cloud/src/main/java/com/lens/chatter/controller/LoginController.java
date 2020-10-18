package com.lens.chatter.controller;

import com.lens.chatter.model.dto.user.LoginDto;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Api(value = "Login", tags = {"Login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "Login with the username (email) and password", response = LoginResource.class)
    @PostMapping("")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(loginService.login(loginDto));
    }


    @ApiOperation(value = "Update token of user by using old non-expired token", response = String.class)
    @GetMapping("/update-token")
    public ResponseEntity tokenUpdate(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(loginService.updateToken(token));
    }
}
