package com.lens.chatter.controller;//package com.lens.chatter.controller;

import com.lens.chatter.model.dto.user.RegisterDto;
import com.lens.chatter.model.dto.user.UpdatePasswordDto;
import com.lens.chatter.model.resource.user.CompleteUserResource;
import com.lens.chatter.model.resource.user.MinimalUserResource;
import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Api(value = "User Profile", tags = {"User Profile"})
@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private JwtResolver jwtResolver;

    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

    @ApiOperation(value = "Return current users profile information", response = CompleteUserResource.class)
    @GetMapping("/get-self-profile")
    public ResponseEntity getSelfProfile(@RequestHeader("Authorization") String token) {
        UUID userId = jwtResolver.getIdFromToken(token);
        logger.info(String.format("Requesting getSelfProfile with userId: %s ", userId));
        CompleteUserResource user = userProfileService.getSelfProfile(userId);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Return profile of a given user. If the user is private and you don't follow it then returns null",
            response = MinimalUserResource.class)
    @GetMapping("/get-other-profile")
    public ResponseEntity getOtherProfile(@RequestHeader("Authorization") String token, @RequestParam("email") String email) {
        UUID userId = jwtResolver.getIdFromToken(token);
        logger.info(String.format("Requesting getOtherProfile with userId: %s ", userId));
        MinimalUserResource user = userProfileService.getOtherProfile(email);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Update a profile with the given info", response = CompleteUserResource.class)
    @PutMapping("/update-profile")
    public ResponseEntity updateUserProfile(@RequestHeader("Authorization") String token, @RequestBody @Valid RegisterDto userDto) {
        UUID userId = jwtResolver.getIdFromToken(token);
        logger.info(String.format("Requesting updateUserProfile with userId: %s ", userId));
        CompleteUserResource user = userProfileService.updateProfile(userId, userDto);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Update password with the old password and the new password", response = CompleteUserResource.class)
    @PutMapping("/update-password")
    public ResponseEntity updatePassword(@RequestHeader("Authorization") String token, @RequestBody UpdatePasswordDto updatePasswordDto) {
        UUID userId = jwtResolver.getIdFromToken(token);
        logger.info(String.format("Requesting updatePassword with userId: %s ", userId));
        CompleteUserResource user = userProfileService.updatePassword(userId, updatePasswordDto);
        return ResponseEntity.ok(user);
    }
}
