package com.lens.chatter.controller;

import com.lens.chatter.model.dto.user.LoginDto;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Api(value = "Login", tags = {"Login"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ApiOperation(value = "Login with the username (email) and password", response = LoginResource.class)
    @PostMapping("")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        logger.info(String.format("Requesting login user's mail: %s ", loginDto.getEmail()));
        return ResponseEntity.ok(loginService.login(loginDto));
    }


    @ApiOperation(value = "Update token of user by using old non-expired token", response = LoginResource.class)
    @GetMapping("/update-token")
    public ResponseEntity<LoginResource> tokenUpdate(@RequestHeader("Authorization") String token) {
        logger.info(String.format("Requesting tokenUpdate"));
        return ResponseEntity.ok(loginService.updateToken(token));
    }
}
