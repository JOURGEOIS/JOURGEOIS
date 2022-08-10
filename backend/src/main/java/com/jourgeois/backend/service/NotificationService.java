package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.jourgeois.backend.api.dto.notification.NotificationDTO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.util.NotificationType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public static final String ROOT_COLLECTION_NAME = "jourgeois";
    public static final String NOTIFICATION_COLLECTION_NAME = "notification";

    public boolean followNotification(Member to, Member from) throws Exception {
        if(to.getUid() == from.getUid()) return true;
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(to.getUid())).collection(NOTIFICATION_COLLECTION_NAME).document();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setType(NotificationType.FOLLOW);
        notificationDTO.setFrom(from.getNickname());
        notificationDTO.setUid(from.getUid());
        notificationDTO.setImg(S3Util.s3urlFormatter(from.getProfileImg()));
        notificationDTO.setTimestamp(FieldValue.serverTimestamp());

        ApiFuture<WriteResult> result = docRef.set(notificationDTO);
        System.out.println("Create Follow Notification - " + result.get().getUpdateTime());

        return true;
    }

    public boolean likeNotification(Member to, Member from, Post post) throws Exception {
        if(to.getUid() == from.getUid()) return true;
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(to.getUid())).collection(NOTIFICATION_COLLECTION_NAME).document();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setType(NotificationType.LIKE);
        notificationDTO.setFrom(from.getNickname());
        notificationDTO.setUid(from.getUid());
        notificationDTO.setImg(S3Util.s3urlFormatter(from.getProfileImg()));
        notificationDTO.setPostId(post.getId());
        notificationDTO.setTimestamp(FieldValue.serverTimestamp());

        ApiFuture<WriteResult> result = docRef.set(notificationDTO);
        System.out.println("Create Like Notification - " + result.get().getUpdateTime());

        return true;
    }

    public boolean commentNotification(Member to, Member from, Post post) throws Exception {
        if(to.getUid() == from.getUid()) return true;
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(to.getUid())).collection(NOTIFICATION_COLLECTION_NAME).document();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setType(NotificationType.COMMENT);
        notificationDTO.setFrom(from.getNickname());
        notificationDTO.setUid(from.getUid());
        notificationDTO.setImg(S3Util.s3urlFormatter(from.getProfileImg()));
        notificationDTO.setPostId(post.getId());
        notificationDTO.setTimestamp(FieldValue.serverTimestamp());

        ApiFuture<WriteResult> result = docRef.set(notificationDTO);
        System.out.println("Create Comment Notification - " + result.get().getUpdateTime());

        return true;
    }
}
