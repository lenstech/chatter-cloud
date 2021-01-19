package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.enums.Role;
import com.lens.chatter.service.ProfilePhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 5 Nis 2020
 */

@RestController
@RequestMapping("/profile-photo")
@Api(value = "Profile Photo", tags = {"Profile Photo"})
public class ProfilePhotoController {

    private static final Logger logger = LoggerFactory.getLogger(ProfilePhotoController.class);
    @Autowired
    private ProfilePhotoService service;
    @Autowired
    private AuthorizationConfig authorizationConfig;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) {
        UUID userId = authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        logger.info(String.format("Requesting uploadProfilePhoto user's id: %s ", userId));
        return ResponseEntity.ok(service.uploadImage(file, userId));
    }

    @GetMapping(value = "/get", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePhoto(@RequestParam("userId") UUID userId) {
        logger.info(String.format("Requesting getProfilePhoto user's id: %s ", userId));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Chatter Profile Photo\"")
                .body(service.getPhoto(userId));
    }

    @DeleteMapping()
    @ApiOperation("Delete photo of defect by defectId")
    public ResponseEntity<String> deleteDefectPhotobyDefectId(@RequestHeader("Authorization") String token) {
        UUID userId = authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        service.deleteSelfProfilePhoto(userId);
        return ResponseEntity.ok(HttpSuccessMessagesConstants.SUCCESSFULLY_DELETED);
    }
}
