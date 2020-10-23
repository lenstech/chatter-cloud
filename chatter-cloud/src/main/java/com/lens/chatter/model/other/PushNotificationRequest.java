package com.lens.chatter.model.other;

import com.lens.chatter.constant.NotificationConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Created by Emir Gökdemir
 * on 23 Ağu 2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationRequest {

    @NotNull
    private String title;
    @NotNull
    private String body;
    private String topic = NotificationConstants.DEFAULT_TITLE;
    @Nullable
    private String token;

    public PushNotificationRequest(String title, String body, String topic) {
        this.title = title;
        this.body = body;
        this.topic = topic;
    }
}
