package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.resource.user.LoginResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.lens.chatter.constant.HttpSuccessMessagesConstants.MAIL_SEND_YOUR_EMAIL;
import static com.lens.chatter.constant.HttpSuccessMessagesConstants.PASSWORD_WAS_CHANGED;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@RestController
@RequestMapping(value = {"/reset-password"})
@Api(value = "Reset Forgotten Password", tags = {"Password Reset"})
public class ResetPasswordController {

    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    @Autowired
    private JwtResolver jwtResolver;
    @Autowired
    private ResetPasswordService resetPasswordService;
    @Autowired
    private AuthorizationConfig authorizationConfig;

    @ApiOperation(value = "Send a reset password URL to the email of the user", response = String.class)
    @GetMapping("/mail-request")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam("email") String email) {
        logger.info(String.format("Requesting resetPasswordRequest user's email: %s ", email));
        resetPasswordService.resetPasswordRequest(email);
        return ResponseEntity.ok(MAIL_SEND_YOUR_EMAIL);
    }

    @ApiOperation(value = "User can reset his password by new password and the token sent his mail address ", response = LoginResource.class)
    @PutMapping("/confirm-and-change")
    public ResponseEntity<LoginResource> changePassword(@RequestParam("password") String password,
                                                        @RequestHeader("Authorization") String resetPasswordToken,
                                                        @RequestHeader("FirebaseToken") String firebaseToken) {
        UUID userId = jwtResolver.getIdFromToken(resetPasswordToken);
        logger.info(String.format("Requesting resetPassword with userId: %s ", userId));
        return ResponseEntity.ok(resetPasswordService.changePassword(password, userId));
    }

    @ApiOperation(value = "ADMIN or FIRM_ADMIN can reset an user password by its mail address and new password", response = String.class)
    @PostMapping("/by-admin")
    public ResponseEntity<String> resetPasswordByAdmin(@RequestParam("new-password") String newPassword,
                                                       @RequestParam("email") String email,
                                                       @RequestHeader("Authorization") String token) {
        logger.info(String.format("Requesting resetPasswordByAdmin with adminId: %s  and emailOfUser: %s", jwtResolver.getIdFromToken(token), email));
        authorizationConfig.permissionCheck(token, Role.BRANCH_ADMIN);
        resetPasswordService.changePasswordByAdmin(email, newPassword, token);
        return ResponseEntity.ok(PASSWORD_WAS_CHANGED);
    }

}
