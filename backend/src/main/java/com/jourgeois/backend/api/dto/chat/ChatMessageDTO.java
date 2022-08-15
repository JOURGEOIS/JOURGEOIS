package com.jourgeois.backend.api.dto.chat;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.FieldValue;
import com.jourgeois.backend.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessageDTO {
    // 송신자 정보
    private String chatRoomId;
    private Long sender;
    private Long receiver;
    
    // 메세지 정보
    private String message;
    
    // 내가 읽었는지 여부 표시 -> 보낸 사람이 내가 아닌 메세지 중 가장 최근 메세지만 체크
    private Boolean isRead;
    private Timestamp timestamp;
    public ChatMessageDTO() {
        this.isRead = false;
    }
}
