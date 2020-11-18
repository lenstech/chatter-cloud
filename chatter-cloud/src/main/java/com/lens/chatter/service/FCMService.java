package com.lens.chatter.service;

import com.google.firebase.messaging.*;
import com.lens.chatter.constant.NotificationConstants;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.other.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emir Gökdemir
 * on 20 Eki 2020
 */

@Service
public class FCMService {

    private final Logger logger = LoggerFactory.getLogger(FCMService.class);

    private AndroidConfig setAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.NORMAL)
                .setNotification(AndroidNotification.builder()
                        .setSound(NotificationConstants.DEFAULT_SOUND)
                        .setColor(NotificationConstants.DEFAULT_COLOR)
                        .setTag(topic)
                        .build())
                .build();
    }

    private ApnsConfig setApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = setAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = setApnsConfig(request.getTopic());
        return Message.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .build());
    }

    private Message getPreconfiguredMessage(PushNotificationRequest request, @Nullable Map<String, String> data) {
        Message.Builder builder = getPreconfiguredMessageBuilder(request);
        if (data != null && !data.isEmpty()) {
            builder.putAllData(data);
        }
        String token = request.getToken(); //device token
        if (token != null && !token.isEmpty()) {
            builder.setToken(request.getToken());
        }
        return builder.setTopic(request.getTopic()).build();
    }

    public String sendMessage(@Nullable Map<String, String> data, PushNotificationRequest request) {
        Message message = getPreconfiguredMessage(request, data);
        String response = sendAndGetResponse(message);
        logger.info("Sent message Topic: " + request.getTopic()
                + ", Token: " + request.getToken()
                + ", Title: " + request.getTitle()
                + ", Body: " + request.getBody()
                + " and response: " + response);
        return response;
    }

    private String sendAndGetResponse(Message message) {
        try {
            return FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (InterruptedException ie) {
            throw new BadRequestException("InterruptedException");
        } catch (ExecutionException ee) {
            throw new BadRequestException("ExecutionException");
        }
    }


}
