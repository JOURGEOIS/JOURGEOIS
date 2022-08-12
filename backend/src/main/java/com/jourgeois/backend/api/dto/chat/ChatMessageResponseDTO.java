package com.jourgeois.backend.api.dto.chat;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class ChatMessageResponseDTO {
//    private String chatRoomId;
    private ChatMessageDTO chatMessageDTO;
}
