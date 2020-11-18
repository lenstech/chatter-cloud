package com.lens.chatter.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.lens.chatter.enums.ChannelType;
import com.lens.chatter.model.other.FirebaseDefaultChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 18 Kas 2020
 */
@Service
public class CreateMessageGroupService {

    private final Logger logger = LoggerFactory.getLogger(CreateMessageGroupService.class);

    final Firestore firestore = FirestoreClient.getFirestore();

    public void saveFirebaseChannel(UUID id, UUID firmId, String name, ChannelType channelType) {
        logger.info(String.format("Requesting saveFirebaseChannel " + channelType.toValue() + "'s id: %s ", id));
        DocumentReference documentReference = firestore.collection("firms").document(firmId.toString());
        CollectionReference channelReference = documentReference.collection("channels");
        channelReference.add(new FirebaseDefaultChannel(name, channelType.getInitial() + "-" + id, new ArrayList<>()));
        documentReference.collection("users-channels");
        //todo: userlar eklenecek. eklenen channel'ın id'si kullanılarak.
    }

}
