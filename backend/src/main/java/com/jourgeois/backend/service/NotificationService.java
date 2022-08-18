package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.jourgeois.backend.api.dto.notification.NotificationDTO;
import com.jourgeois.backend.api.dto.notification.NotificationResponseDTO;
import com.jourgeois.backend.api.dto.notification.OpponentDTO;
import com.jourgeois.backend.api.dto.post.PostMetaDTO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.PostRepository;
import com.jourgeois.backend.util.NotificationType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class NotificationService {

    public static final String ROOT_COLLECTION_NAME = "jourgeois";
    public static final String NOTIFICATION_COLLECTION_NAME = "notification";

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public NotificationService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public boolean followNotification(Member to, Member from) throws Exception {
        if(to.getUid() == from.getUid()) return true;
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(to.getUid())).collection(NOTIFICATION_COLLECTION_NAME).document();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(docRef.getId());
        notificationDTO.setType(NotificationType.FOLLOW);
        notificationDTO.setUid(from.getUid());
        notificationDTO.setTimestamp(Timestamp.now());

        ApiFuture<WriteResult> result = docRef.set(notificationDTO);
//        System.out.println("Create Follow Notification - " + result.get().getUpdateTime());

        return true;
    }

    public boolean likeNotification(Member to, Member from, Post post) throws Exception {
        if(to.getUid() == from.getUid()) return true;
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(to.getUid())).collection(NOTIFICATION_COLLECTION_NAME).document();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(docRef.getId());
        notificationDTO.setType(NotificationType.LIKE);
        notificationDTO.setPostId(post.getId());
        notificationDTO.setUid(from.getUid());
        notificationDTO.setTimestamp(Timestamp.now());


        ApiFuture<WriteResult> result = docRef.set(notificationDTO);
//        System.out.println("Create Like Notification - " + result.get().getUpdateTime());

        return true;
    }

    public boolean commentNotification(Member to, Member from, Post post) throws Exception {
        if(to.getUid() == from.getUid()) return true;
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(to.getUid())).collection(NOTIFICATION_COLLECTION_NAME).document();

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(docRef.getId());
        notificationDTO.setType(NotificationType.COMMENT);
        notificationDTO.setPostId(post.getId());
        notificationDTO.setUid(from.getUid());
        notificationDTO.setTimestamp(Timestamp.now());

        ApiFuture<WriteResult> result = docRef.set(notificationDTO);
//        System.out.println("Create Comment Notification - " + result.get().getUpdateTime());

        return true;
    }

    public boolean changeToBeRead(Long uid, Map<String, String> notificationId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(uid)).collection(NOTIFICATION_COLLECTION_NAME).document(notificationId.get("notiId"));

        ApiFuture<WriteResult> future = docRef.update("isRead", true);
        WriteResult result = future.get();
//        System.out.println("changeToBeRead result - " + result);

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

        return true;
    }

    public Map<String, Object> getNotificationList(Long uid, int page) throws ExecutionException, InterruptedException, TimeoutException {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String chkDate = SDF.format(calendar.getTime());
        calendar.add(Calendar.DATE, -15);
        chkDate = SDF.format(calendar.getTime());
        Timestamp halfday = Timestamp.of(java.sql.Timestamp.valueOf(chkDate));

        Map<String, Object> result = new HashMap<>();
        List<NotificationResponseDTO> notificationResponseDTOList = new ArrayList<>();

        Firestore db = FirestoreClient.getFirestore();
        Query firstPage = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(uid)).collection(NOTIFICATION_COLLECTION_NAME)
                .whereGreaterThan("timestamp", halfday)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        // Wait for the results of the API call, waiting for a maximum of 30 seconds for a result.
        ApiFuture<QuerySnapshot> future = firstPage.get();
        List<QueryDocumentSnapshot> docs = future.get(30, TimeUnit.SECONDS).getDocuments();
        if(docs.size() <= page){
            result.put("size", page);
            return result;
        }

        QueryDocumentSnapshot lastDoc = docs.get(page);
        ApiFuture<QuerySnapshot> query  = db.collection(ROOT_COLLECTION_NAME).document(String.valueOf(uid)).collection(NOTIFICATION_COLLECTION_NAME)
                .whereGreaterThan("timestamp", halfday)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAt(lastDoc)
                .limit(10)
                .get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        for (DocumentSnapshot document : documents) {
            NotificationDTO notificationDTO = document.toObject(NotificationDTO.class);
            Long opponentUid = notificationDTO.getUid();
            Member opponent = memberRepository.findById(opponentUid).orElseThrow(()-> new NoSuchElementException("상대 유저 정보가 없습니다."));
            OpponentDTO notiOpponentDTO = OpponentDTO.builder()
                    .uid(opponent.getUid())
                    .img(S3Util.s3urlFormatter(opponent.getProfileImg()))
                    .nickname(opponent.getNickname())
                    .build();

            PostMetaDTO postMetaDTO = postRepository.getPostMetaData(notificationDTO.getPostId());

            notificationResponseDTOList.add(NotificationResponseDTO.builder()
                            .notification(notificationDTO)
                            .opponent(notiOpponentDTO)
                            .postMetaInfo(postMetaDTO)
                            .build());
        }

        result.put("size", page+documents.size());
        result.put("list", notificationResponseDTOList);
        return result;
    }
}
