package com.jourgeois.backend.api.dto.chat;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.FieldValue;
import com.jourgeois.backend.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
public class ChatRoomDTO {
    // 송수신자 정보
    private String chatRoomId;
    
    // 상대방
    private ChatOpponentDTO opponent;

    // 메세지 정보
    private ChatMessageDTO lastMessage;
}
