package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jourgeois.backend.api.dto.notification.NotificationDTO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.util.NotificationType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

    public boolean changeToBeRead(Long uid, Map<String, String> notificationId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(uid)).collection(NOTIFICATION_COLLECTION_NAME).document(notificationId.get("notiId"));

        ApiFuture<WriteResult> future = docRef.update("isRead", true);
        WriteResult result = future.get();
        System.out.println("changeToBeRead result - " + result);

        return true;
    }

    public boolean changeToBeReadAll(Long uid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(uid)).collection(NOTIFICATION_COLLECTION_NAME);

        WriteBatch batch = db.batch();
        for(DocumentReference doc : collectionRef.listDocuments()) {
            batch.update(doc, "isRead", true);
        }

        ApiFuture<List<WriteResult>> future = batch.commit();

        for(WriteResult result : future.get()) {
            System.out.println("Update time : " + result.getUpdateTime());
        }

        return true;
    }
}
