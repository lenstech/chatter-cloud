package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.enums.Role;
import com.lens.chatter.service.TokenService;
import com.lens.chatter.service.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

import static com.lens.chatter.constant.HttpSuccessMessagesConstants.*;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@RestController
@RequestMapping(value = {"/reset-password"})
@Api(value = "Reset Forgotten Password", tags = {"Password Reset"})
public class ResetPasswordController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @ApiOperation(value = "Send a reset password URL to the email of the user", response = String.class)
    @GetMapping("/mail-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam("email")String email) {
        resetPasswordService.resetPasswordRequest(email);
        return ResponseEntity.ok(MAIL_SEND_YOUR_EMAIL);
    }

    @ApiOperation(value = "User can reset his password by new password and the token sent his mail address ", response = String.class)
    @PutMapping("/confirm-and-change")
    public ResponseEntity<String> changePassword(@RequestParam("password") String password,
                                                @RequestHeader("Authorization") String confirmationToken) {
        resetPasswordService.changePassword(password, confirmationToken);
        return ResponseEntity.ok(YOUR_PASSWORD_WAS_CHANGED);
    }

    @ApiOperation(value = "ADMIN or FIRM_ADMIN can reset an user password by its mail address and new password", response = String.class)
    @PostMapping("/by-admin")
    public ResponseEntity<String> resetPasswordByAdmin(@RequestParam("new-password") String newPassword,
                                                       @RequestParam("email") String email,
                                                       @RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        resetPasswordService.changePasswordByAdmin(email,newPassword,token);
        return ResponseEntity.ok(PASSWORD_WAS_CHANGED);
    }

}
