package com.lens.chatter.controller;

import com.lens.chatter.service.ConfirmationTokenService;
import com.lens.chatter.service.ResetPasswordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.lens.chatter.constant.HttpSuccesMessagesConstants.MAIL_SEND_YOUR_EMAIL;
import static com.lens.chatter.constant.HttpSuccesMessagesConstants.YOUR_PASSWORD_WAS_CHANGED;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@RestController
@RequestMapping(value = {"/reset-password"})
@Api(value = "Reset Forgotten Password", tags = {"Password Reset For Forgotten"})
public class ResetPasswordController {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @ApiOperation(value = "Send a reset password URL to the email of the user", response = String.class)
    @GetMapping("/send-mail")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam("email") String email) {
        confirmationTokenService.sendResetPasswordsToken(email);
        return ResponseEntity.ok(MAIL_SEND_YOUR_EMAIL);
    }

    @ApiOperation(value = "User can reset his password by using the token sent his mail address and new password", response = String.class)
    @GetMapping("/confirm")
    public ResponseEntity<String> resetForgottenPassword(@RequestParam("password") String password,
                                                @RequestParam("token") String confirmationToken) {
        resetPasswordService.resetPassword(password, confirmationToken);
        return ResponseEntity.ok(YOUR_PASSWORD_WAS_CHANGED);
    }

}
