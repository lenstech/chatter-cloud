package com.lens.chatter.service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.lens.chatter.model.entity.Firm;
import com.lens.chatter.model.other.FirebaseDefaultChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Emir Gökdemir
 * on 18 Kas 2020
 */
@Service
public class CreateMessageGroupService {

    private final Logger logger = LoggerFactory.getLogger(CreateMessageGroupService.class);

    public void saveFirebaseFirm(Firm firm) {
        logger.info(String.format("Requesting saveFirebaseFirm firm's id: %s ", firm.getId()));
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection("firms").document(firm.getId().toString());
        documentReference.collection("users-channels");
        CollectionReference channelReference = documentReference.collection("channels");
        channelReference.add(new FirebaseDefaultChannel(firm.getName(), "f-" + firm.getId(), new ArrayList<>()));
    }

}
