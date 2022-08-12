package com.jourgeois.backend.api.dto.notification;

import com.google.cloud.Timestamp;
import com.jourgeois.backend.util.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    private String notificationId;
    // FOLLOW, LIKE, COMMENT
    private NotificationType type;

    // 상대방 정보
    private Long uid;
    private Boolean isRead;
    private Timestamp timestamp;
    public NotificationDTO() {
        this.isRead = false;
    }
}
