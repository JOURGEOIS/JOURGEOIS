package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jourgeois.backend.api.dto.member.FollowerDTO;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseService {

    public static final String COLLECTION_NAME = "user";

    public void insertUser() throws Exception {
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference ref = db.getReference("notice");
//        DatabaseReference child = ref.child("uid");
//        DatabaseReference nextNotiRef = child.push();
//        String postId = nextNotiRef.getKey();
//        DatabaseReference saveNoti = child.child(postId);
//
//
//        FollowerDTO followerDTO = FollowerDTO.builder()
//                .uid(1L).introduce("Hello, I m Test.").build();
//
//        saveNoti.setValueAsync(followerDTO);

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("users").document("aturing");
        Map<String, Object> data = new HashMap<>();
        data.put("first", "Alan");
        data.put("middle", "Mathison");
        data.put("last", "Turing");
        data.put("born", 1912);

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }
}
