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
    private Long from;
    private Long to;
    // 메세지 정보
    private String message;
    private Boolean isRead;
    private Timestamp timestamp;
    public ChatMessageDTO() {
        this.isRead = false;
    }
}
