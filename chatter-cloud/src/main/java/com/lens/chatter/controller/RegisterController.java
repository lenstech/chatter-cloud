package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.enums.Role;
import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.dto.user.InviteMailDto;
import com.lens.chatter.model.resource.user.CompleteUserResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.lens.chatter.constant.HttpSuccessMessagesConstants.INVITATION_MAIL_IS_SENT;
import static com.lens.chatter.constant.HttpSuccessMessagesConstants.YOUR_MAIL_WAS_CONFIRMED;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@RestController
@RequestMapping(value = {"/register"})
@Api(value = "Registration", tags = {"Registration"})
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @Autowired
    private JwtResolver resolver;


    @ApiOperation(value = "Register a user with the needed information", response = CompleteUserResource.class)
    @PostMapping("/user")
    public ResponseEntity<CompleteUserResource> registerUser(@RequestBody @Valid RegisterDto registerDto) {
        return ResponseEntity.ok(registerService.register(registerDto));
    }

    @ApiOperation(value = "Confirm a registration by using the link from the user's confirmation mail", response = String.class)
    @GetMapping("/confirm-register")
    public ResponseEntity<String> confirmRegister(@RequestHeader("Authorization") String confirmationToken) {
        registerService.confirmRegister(confirmationToken);
        return ResponseEntity.ok(YOUR_MAIL_WAS_CONFIRMED);
    }

    @ApiOperation(value = "Send registration mail", response = String.class)
    @PostMapping("/send-invite-mail")
    public ResponseEntity<String> sendInviteMail(@RequestHeader("Authorization") String token,
                                                  @RequestBody InviteMailDto inviteMailDto) {
        authorizationConfig.permissionCheck(token, Role.DEPARTMENT_ADMIN);
        registerService.sendInviteMail(token, inviteMailDto);
        return ResponseEntity.ok(INVITATION_MAIL_IS_SENT);
    }

    @ApiOperation(value = "Confirm invite mail and get the token's information.", response = InviteMailDto.class)
    @GetMapping("/get-invite-token-info")
    public ResponseEntity<InviteMailDto> getInviteTokenInfo(@RequestHeader("Authorization") String inviteToken) {
        return ResponseEntity.ok(registerService.getInviteTokenInfo(inviteToken));
    }

}
