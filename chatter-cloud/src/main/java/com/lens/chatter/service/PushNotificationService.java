package com.lens.chatter.service;

import com.lens.chatter.model.other.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Emir Gökdemir
 * on 20 Eki 2020
 */

@Service
public class PushNotificationService {

    @Value("#{${app.notifications.defaults}}")
    private Map<String, String> defaults;

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    //    @Scheduled(initialDelay = 60000, fixedDelay = 60000)    //todo: testten sonra sil.
    public void sendSamplePushNotification() {
        fcmService.sendMessage(new HashMap<>(), getSamplePushNotificationRequest());
    }

    public String sendPushNotification(PushNotificationRequest request) {
        return fcmService.sendMessage(getSamplePayloadData(), request);
    }

    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", defaults.get("payloadMessageId"));
        pushData.put("text", defaults.get("payloadData") + " " + LocalDateTime.now());
        return pushData;
    }

    private PushNotificationRequest getSamplePushNotificationRequest() {
        PushNotificationRequest request = new PushNotificationRequest(defaults.get("title"),
                defaults.get("message"),
                defaults.get("topic"));
        return request;
    }
}
