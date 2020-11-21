package com.lens.chatter.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.lens.chatter.constant.ErrorConstants;
import com.lens.chatter.enums.ChannelType;
import com.lens.chatter.exception.BadRequestException;
import com.lens.chatter.model.other.FirebaseDefaultChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by Emir Gökdemir
 * on 18 Kas 2020
 */
@Service
public class CreateMessageGroupService {

    private final Logger logger = LoggerFactory.getLogger(CreateMessageGroupService.class);

    public void saveFirebaseChannel(UUID id, UUID firmId, String name, ChannelType channelType) {
        logger.info(String.format("Requesting saveFirebaseChannel " + channelType.toValue() + "'s id: %s ", id));
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection("firms").document(firmId.toString());
        CollectionReference channelReference = documentReference.collection("channels");
        ApiFuture<DocumentReference> future = channelReference.add(new FirebaseDefaultChannel(name, channelType.getInitial() + "-" + id, new ArrayList<>()));
        Map<String, Object> userChannels = new HashMap<>();
        try {
            userChannels.put("channelId", future.get().getId());
            userChannels.put("participantId",channelType.getInitial() + "-" + id);
            documentReference.collection("users-channels").add(userChannels);
        } catch (InterruptedException | ExecutionException e) {
            throw new BadRequestException(ErrorConstants.USER_CHANNELS_CANNOT_BE_CREATED);
        }
    }

}
