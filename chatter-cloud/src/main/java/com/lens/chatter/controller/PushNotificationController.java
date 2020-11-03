package com.lens.chatter.controller;

import com.lens.chatter.model.other.PushNotificationRequest;
import com.lens.chatter.service.PushNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Emir Gökdemir
 * on 20 Eki 2020
 */
@RestController
@RequestMapping(value = {"/push-notification"})
@Api(value = "Push Notification", tags = {"Push Notification"})
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationController.class);

    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/notification")
    @ApiOperation(value = "Send push notification", response = String.class)
    public ResponseEntity<String> sendPushNotification(@RequestBody @Valid PushNotificationRequest request) {
        logger.info(String.format("Requesting sendPushNotification request: %s ", request.toString()));
        return ResponseEntity.ok(pushNotificationService.sendPushNotification(request));
    }

    @GetMapping("/notification")
    @ApiOperation(value = "Send sample notification", response = String.class)
    public ResponseEntity<StringBuilder> sendSampleNotification() {
        logger.info("Requesting sendSampleNotification");
        pushNotificationService.sendSamplePushNotification();
        return ResponseEntity.ok(new StringBuilder("Sample Push Notification is sent!"));
    }
}
