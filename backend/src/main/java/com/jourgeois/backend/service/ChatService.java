package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.jourgeois.backend.api.dto.chat.ChatMessageDTO;

import com.jourgeois.backend.api.dto.notification.OpponentDTO;
import com.jourgeois.backend.api.dto.chat.ChatRoomDTO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

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

        List<ChatRoomDTO> chatRooms = new LinkedList<>();

        for(QueryDocumentSnapshot chatRoomRef : chatRoomRefs) {
            DocumentReference lastMessageRef = chatRoomRef.get("lastMessage", DocumentReference.class);

            System.out.println(lastMessageRef.getId());

            ChatMessageDTO lastMessage = lastMessageRef.get().get().toObject(ChatMessageDTO.class);

                ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
                chatRoomDTO.setChatRoomId(lastMessage.getChatRoomId());

                List<Long> users = (List<Long>) chatRoomRef.get("users");
                int opponentUserIdx = (users.indexOf(myUid)^1);
                Long opponentUserId = users.get(opponentUserIdx);
                System.out.println("================" + opponentUserId);
                Member opponent = memberRepository.findById(opponentUserId).orElseThrow(() -> new NoSuchElementException("상대 유저 정보가 없습니다."));
                OpponentDTO chatOpponentDTO = OpponentDTO.builder()
                        .uid(opponent.getUid())
                        .img(S3Util.s3urlFormatter(opponent.getProfileImg()))
                        .nickname(opponent.getNickname())
                        .build();
                chatRoomDTO.setOpponent(chatOpponentDTO);
                chatRoomDTO.setLastMessage(lastMessage);
                chatRoomDTO.setHasNewMessage(lastMessage.getSender() != myUid && !lastMessage.getIsRead());chatRooms.add(chatRoomDTO);
        }

        Collections.sort(chatRooms);

//        for(ChatRoomDTO chatRoom : chatRooms) {
//            System.out.println(chatRoom.toString());
//        }

        return chatRooms;
    }

    public boolean sendMessage(ChatMessageDTO chatMessage) throws NoSuchElementException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        String chatRoomId = chatMessage.getChatRoomId();
        DocumentReference msg = null;
        // 새로운 채팅 개시
        if(chatRoomId == null || chatRoomId.isEmpty()) {
            msg = db.collection(ROOT_CHAT_COLLECTION_NAME).document().collection("messages").document();

            chatRoomId = msg.getParent().getParent().getId();
            List<Long> users = new ArrayList<>();
            users.add(chatMessage.getSender());
            users.add(chatMessage.getReceiver());

            Map<String, Object> usersFieldValue = new HashMap<>();
            usersFieldValue.put("users", users);
            System.out.println(chatRoomId);
            usersFieldValue.put("lastMessage", msg);

            ApiFuture<WriteResult> result = db.collection(ROOT_CHAT_COLLECTION_NAME).document(chatRoomId).set(usersFieldValue);
            System.out.println("채팅방 생성 성공 : " + result.get().getUpdateTime());

            chatMessage.setChatRoomId(chatRoomId);
        }
        // 기존에 있는 채팅
        else {
            msg = db.collection(ROOT_CHAT_COLLECTION_NAME).document(chatRoomId).collection("messages").document();
            List<Long> users = new ArrayList<>();
            users.add(chatMessage.getSender());
            users.add(chatMessage.getReceiver());

            Map<String, Object> usersFieldValue = new HashMap<>();
            usersFieldValue.put("users", users);
            System.out.println(chatRoomId);
            usersFieldValue.put("lastMessage", msg);

            ApiFuture<WriteResult> result = db.collection(ROOT_CHAT_COLLECTION_NAME).document(chatRoomId).set(usersFieldValue);
            System.out.println("채팅방 갱신 성공 : " + result.get().getUpdateTime());
        }
        chatMessage.setTimestamp(Timestamp.now());
        ApiFuture<WriteResult> result = msg.set(chatMessage);

        System.out.println("메세지 전송 성공 : " + result.get().getUpdateTime());
        
        return true;
    }
}
