package com.jourgeois.backend.api.dto.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    // follow, message
    private String type;

    private String to;
    private String from;

    // 상대방 정보보
    private String img;
    private String nickname;

    private String isRead;
}
