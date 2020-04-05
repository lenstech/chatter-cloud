package com.lens.chatter.controller;

import com.lens.chatter.security.JwtResolver;
import com.lens.chatter.service.ProfilePhotoService;
import io.swagger.annotations.Api;
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

    @Autowired
    private ProfilePhotoService service;

    @Autowired
    private JwtResolver jwtResolver;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProfilePhoto(@RequestParam("file") MultipartFile file, @RequestHeader String token) {
        return ResponseEntity.ok(service.uploadImage(file, jwtResolver.getIdFromToken(token)));
    }

    @GetMapping(value = "/get", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePhoto(@RequestParam("userId") UUID userId) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Chatter Profile Photo\"")
                .body(service.getPhoto(userId));
    }
}
