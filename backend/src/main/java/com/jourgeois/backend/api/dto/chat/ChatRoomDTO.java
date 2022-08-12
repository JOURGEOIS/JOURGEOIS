package com.jourgeois.backend.api.dto.chat;

import com.jourgeois.backend.api.dto.notification.OpponentDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomDTO {
    // 송수신자 정보
    private String chatRoomId;
    
    // 상대방
    private OpponentDTO opponent;

    // 메세지 정보
    private ChatMessageDTO lastMessage;
}
