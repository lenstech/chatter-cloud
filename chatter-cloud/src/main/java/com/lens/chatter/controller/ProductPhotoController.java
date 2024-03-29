package com.lens.chatter.controller;

import com.lens.chatter.configuration.AuthorizationConfig;
import com.lens.chatter.constant.HttpSuccessMessagesConstants;
import com.lens.chatter.enums.Role;
import com.lens.chatter.service.ProductPhotoService;
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
@RequestMapping("/product-photo")
@Api(value = "Product Photo", tags = {"Product Photo"})
public class ProductPhotoController {

    @Autowired
    private ProductPhotoService service;

    @Autowired
    private AuthorizationConfig authorizationConfig;

    @PostMapping("")
    @ApiOperation(value = "Upload photo of product by productId", response =  String.class)
    public ResponseEntity<String> uploadProductPhoto(@RequestParam("file") MultipartFile file,
                                                     @RequestParam UUID productId,
                                                     @RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok(service.uploadProductPhoto(file, productId));
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("Get photo of product by productId")
    public ResponseEntity<byte[]> getProductPhoto(@RequestParam("productId") UUID productId,
                                                  @RequestHeader("Authorization") String token) {
        UUID userId = authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"PetsApp Product Photo\"" + productId)
                .body(service.getPhoto(productId));

    }

    @DeleteMapping("/product")
    @ApiOperation(value = "Delete photo of product by productId", response =  String.class)
    public ResponseEntity<String> deleteProductPhoto(@RequestParam("productId") UUID productId,
                                                     @RequestHeader("Authorization") String token) {
        authorizationConfig.permissionCheck(token, Role.BASIC_USER);
        service.deletePhotoByProductId(productId);
        return ResponseEntity.ok(HttpSuccessMessagesConstants.SUCCESSFULLY_DELETED);
    }

//    @DeleteMapping
//    @ApiOperation("Delete photo of product by photoId")
//    public ResponseEntity<String> deletePhoto(@RequestParam("photoId") UUID photoId,
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
