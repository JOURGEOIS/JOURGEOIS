package com.jourgeois.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
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

        List<ChatRoomDTO> chatRooms = new LinkedList<>();

        // 채팅방(firebase doc)의 레퍼런스 가져오기
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
                chatRoomDTO.setHasNewMessage(!lastMessage.getSender().equals(myUid) && !lastMessage.getIsRead());
                chatRooms.add(chatRoomDTO);

        }

        Collections.sort(chatRooms);

        for(ChatRoomDTO chatRoom : chatRooms) {
            System.out.println(chatRoom.toString());
        }

        return chatRooms;
    }

    // ChatMessageDTO
    //     private String chatRoomId;
    //    private Long sender;
    //    private Long receiver;
    //
    //    // 메세지 정보
    //    private String message;
    //
    //    // 내가 읽었는지 여부 표시 -> 보낸 사람이 내가 아닌 메세지 중 가장 최근 메세지만 체크
    //    private Boolean isRead;
    public boolean sendMessage(ChatMessageDTO chatMessage) throws NoSuchElementException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        String chatRoomId = chatMessage.getChatRoomId();
        DocumentReference msg = null;
        // 새로운 채팅 개시
        if (chatRoomId == null || chatRoomId.isEmpty()) {
            msg = db.collection(ROOT_CHAT_COLLECTION_NAME).document().collection("messages").document();

            chatRoomId = msg.getParent().getParent().getId();
            List<Long> users = new ArrayList<>();
            users.add(chatMessage.getSender());
            users.add(chatMessage.getReceiver());

            Map<String, Object> usersFieldValue = new HashMap<>();
            usersFieldValue.put("users", users);
//            System.out.println(chatRoomId);
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
//            System.out.println(chatRoomId);
            usersFieldValue.put("lastMessage", msg);

            ApiFuture<WriteResult> result = db.collection(ROOT_CHAT_COLLECTION_NAME).document(chatRoomId).set(usersFieldValue);
            System.out.println("채팅방 갱신 성공 : " + result.get().getUpdateTime());
        }
        chatMessage.setTimestamp(Timestamp.now());
        ApiFuture<WriteResult> result = msg.set(chatMessage);

        System.out.println("메세지 전송 성공 : " + result.get().getUpdateTime());

        return true;
    }

    // uid = 상대방 id
    public List<ChatMessageResponseDTO> getChatMessages(Long uid, /*int startAfter,*/ String roomId) throws ExecutionException, InterruptedException, TimeoutException {
        Firestore db = FirestoreClient.getFirestore();
        Query firstPage = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING);

        Map<String, Object> result = new HashMap<>();
        // Wait for the results of the API call, waiting for a maximum of 30 seconds for a result.
        ApiFuture<QuerySnapshot> future = firstPage.get();
        List<QueryDocumentSnapshot> docs = future.get(30, TimeUnit.SECONDS).getDocuments();
        /*
        if(docs.size() <= startAfter){

            result.put("size", startAfter);
            return result;
        }
        */
//        QueryDocumentSnapshot lastDoc = docs.get(/*startAfter*/ 0);

        // ============== 제일 마지막 read 처리 =========================
        ApiFuture<QuerySnapshot> query2 = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .whereNotEqualTo("sender", uid)
                .orderBy("sender")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1).get();
        List<QueryDocumentSnapshot> dd = query2.get().getDocuments();

        if(!dd.isEmpty()) {
            DocumentReference dref = db.collection(ROOT_CHAT_COLLECTION_NAME)
                    .document(roomId)
                    .collection("messages")
                    .document(dd.get(0).getId());
            dref.update("isRead", true);
        }
        // ============================================================


        ApiFuture<QuerySnapshot> query = db.collection(ROOT_CHAT_COLLECTION_NAME)
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                //.startAt(lastDoc)
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
        // result.put("size", startAfter+chatRoomRefs.size());
//        result.put("messages", chatMessageResponseDTOList);
        return chatMessageResponseDTOList;
    }
}
