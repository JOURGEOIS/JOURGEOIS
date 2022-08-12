package com.jourgeois.backend.api.dto.chat;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ChatOpponentDTO {
    Long uid;
    String img;
    String nickname;
}
