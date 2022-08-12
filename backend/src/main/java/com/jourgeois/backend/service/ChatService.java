package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.jourgeois.backend.api.dto.chat.ChatMessageDTO;
import com.jourgeois.backend.api.dto.chat.ChatOpponentDTO;
import com.jourgeois.backend.api.dto.chat.ChatRoomDTO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

        List<ChatRoomDTO> chatRooms = new ArrayList<>();

        for(QueryDocumentSnapshot chatRoomRef : chatRoomRefs) {
            CollectionReference messages = chatRoomRef.getReference().collection("messages");

            Query getLastMessage = messages.orderBy("timestamp", Query.Direction.DESCENDING).limit(1);

            ApiFuture<QuerySnapshot> lastMessage = getLastMessage.get();
            List<QueryDocumentSnapshot> documents = lastMessage.get().getDocuments();

            if (!documents.isEmpty()) {
                ChatMessageDTO chatMessageDTO = documents.get(0).toObject(ChatMessageDTO.class);
                ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
                chatRoomDTO.setChatRoomId(messages.getParent().getId());

                Long opponentUid = chatMessageDTO.getFrom().equals(myUid) ? chatMessageDTO.getTo() : chatMessageDTO.getFrom();
                Member opponent = memberRepository.findById(opponentUid).orElseThrow(() -> new NoSuchElementException("상대 유저 정보가 없습니다."));
                ChatOpponentDTO chatOpponentDTO = ChatOpponentDTO.builder()
                        .uid(opponent.getUid())
                        .img(S3Util.s3urlFormatter(opponent.getProfileImg()))
                        .nickname(opponent.getNickname())
                        .build();
                chatRoomDTO.setOpponent(chatOpponentDTO);
                chatRoomDTO.setLastMessage(chatMessageDTO);
                chatRooms.add(chatRoomDTO);
            }

            System.out.println(chatRooms.get(0));
        }


        return chatRooms;
    }
}
