package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.jourgeois.backend.api.dto.chat.ChatMessageDTO;
import com.jourgeois.backend.api.dto.chat.ChatMessageResponseDTO;
import com.jourgeois.backend.api.dto.chat.ChatRoomDTO;
import com.jourgeois.backend.api.dto.notification.OpponentDTO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class ChatService {
    public static final String ROOT_CHAT_COLLECTION_NAME = "juachat";

    private final MemberRepository memberRepository;

    @Autowired
    ChatService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<ChatRoomDTO> loadChatRoom(Long myUid) throws NoSuchElementException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Query getChatRoomAll = db.collection(ROOT_CHAT_COLLECTION_NAME).whereArrayContains("users", myUid);
        ApiFuture<QuerySnapshot> result = getChatRoomAll.get();
        List<QueryDocumentSnapshot> chatRoomRefs = result.get().getDocuments();

        List<ChatRoomDTO> chatRooms = new ArrayList<>();

        for(QueryDocumentSnapshot chatRoomRef : chatRoomRefs) {
            CollectionReference messages = chatRoomRef.getReference().collection("messages");

            Query getLastMessage = messages.orderBy("timestamp", Query.Direction.DESCENDING).limit(1);

            ApiFuture<QuerySnapshot> lastMessage = getLastMessage.get();
            List<QueryDocumentSnapshot> documents = lastMessage.get().getDocuments();

//            if (!documents.isEmpty()) {
//                ChatMessageDTO chatMessageDTO = documents.get(0).toObject(ChatMessageDTO.class);
//                ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
//                chatRoomDTO.setChatRoomId(messages.getParent().getId());
//
//                Long opponentUid = chatMessageDTO.getFrom().equals(myUid) ? chatMessageDTO.getTo() : chatMessageDTO.getFrom();
//                Member opponent = memberRepository.findById(opponentUid).orElseThrow(() -> new NoSuchElementException("상대 유저 정보가 없습니다."));
//                OpponentDTO chatOpponentDTO = OpponentDTO.builder()
//                        .uid(opponent.getUid())
//                        .img(S3Util.s3urlFormatter(opponent.getProfileImg()))
//                        .nickname(opponent.getNickname())
//                        .build();
//                chatRoomDTO.setOpponent(chatOpponentDTO);
//                chatRoomDTO.setLastMessage(chatMessageDTO);
//                chatRooms.add(chatRoomDTO);
//            }

            System.out.println(chatRooms.get(0));
        }


        return chatRooms;
    }

    public Map<String, Object> getChatMessages(Long uid, int page, String roomId) throws ExecutionException, InterruptedException, TimeoutException {
        Firestore db = FirestoreClient.getFirestore();
        Query firstPage = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING);

        Map<String, Object> result = new HashMap<>();
        // Wait for the results of the API call, waiting for a maximum of 30 seconds for a result.
        ApiFuture<QuerySnapshot> future = firstPage.get();
        List<QueryDocumentSnapshot> docs = future.get(30, TimeUnit.SECONDS).getDocuments();
        if(docs.size() <= page){
            result.put("size", page);
            return result;
        }
        QueryDocumentSnapshot lastDoc = docs.get(page);

        // ============== 제일 마지막 read 처리 =========================
        ApiFuture<QuerySnapshot> query2 = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .whereNotEqualTo("sender", uid)
                .orderBy("sender")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1).get();
        List<QueryDocumentSnapshot> dd = query2.get().getDocuments();
        DocumentReference dref = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .document(dd.get(0).getId());
        dref.update("isRead", true);
        // ============================================================

        ApiFuture<QuerySnapshot> query = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAt(lastDoc)
                .limit(50)
                .get();
        List<QueryDocumentSnapshot> chatRoomRefs = query.get().getDocuments();

        List<ChatMessageResponseDTO> chatMessageResponseDTOList = new ArrayList<>();
        for (DocumentSnapshot document : chatRoomRefs) {
            System.out.println(document.getId());
            ChatMessageDTO chatMessageDTO = document.toObject(ChatMessageDTO.class);
            chatMessageResponseDTOList.add(ChatMessageResponseDTO.builder()
                            .chatMessageDTO(chatMessageDTO)
                            .build());
        }
        result.put("size", page+chatRoomRefs.size());
        result.put("messages", chatMessageResponseDTOList);
        return result;
    }
}
