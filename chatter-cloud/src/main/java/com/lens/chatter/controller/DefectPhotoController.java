package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.enums.Role;
import com.lens.chatter.service.DefectPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/defect-photo")
@Api(value = "Defect Photo", tags = {"Defect Photo"})
public class DefectPhotoController {

    @Autowired
    private DefectPhotoService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @PostMapping("")
    @ApiOperation("Upload photo of defect by defectId")
    public ResponseEntity<String> uploadDefectPhoto(@RequestParam("file") MultipartFile file,
                                                    @RequestParam UUID defectId,
                                                    @RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.uploadDefectPhoto(file, defectId));
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("Get photo of defect by defectId")
    public ResponseEntity<byte[]> getDefectPhoto(@RequestParam("defectId") UUID defectId,
                                                 @RequestHeader("Authorization") String token) {
        UUID userId = authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"PetsApp Defect Photo\"" + defectId)
                .body(service.getPhoto(defectId));

    }

    @DeleteMapping("/defect")
    @ApiOperation("Delete photo of defect by defectId")
    public ResponseEntity<String> deleteDefectPhotobyDefectId(@RequestParam("defectId") UUID defectId,
                                                              @RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        service.deletePhotoByDefectId(defectId);
        return ResponseEntity.ok(HttpSuccessMessagesConstants.SUCCESSFULLY_DELETED);
    }
//
//    @DeleteMapping
//    @ApiOperation("Delete photo of defect by photoId")
//    public ResponseEntity<String> deletePhotoById(@RequestParam("photoId") UUID photoId,
//                                                     @RequestHeader("Authorization") String token) {
//        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
//        try {
//            service.deletePhoto(photoId);
//        } catch (Exception e) {
//            return ResponseEntity.ok(HttpSuccessMessagesConstants.DELETION_DID_NOT_OCCURRED);
//        }
//        return ResponseEntity.ok(HttpSuccessMessagesConstants.SUCCESSFULLY_DELETED);
//    }
}
