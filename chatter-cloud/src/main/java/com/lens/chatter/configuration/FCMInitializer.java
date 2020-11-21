package com.lens.chatter.configuration;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Emir Gökdemir
 * on 20 Eki 2020
 */

@Service
public class FCMInitializer {
    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        Date expirationTime = calendar.getTime();
        AccessToken accessToken = new AccessToken("86aa355b853332ebe55f2adc1c16c55d2808285a", expirationTime);

        FirebaseOptions options = FirebaseOptions.builder()
                .setProjectId("lens-chatter")
                .setCredentials(GoogleCredentials.create(accessToken))
                .setDatabaseUrl("https://lens-chatter.firebaseio.com")
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

    }

}
