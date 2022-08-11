package com.jourgeois.backend.api.dto.notification;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.FieldValue;
import com.jourgeois.backend.util.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
//    private String notiId;

    // FOLLOW, LIKE, COMMENT
    private NotificationType type;

    // 상대방 정보
    private String from;
    private Long uid;
    private Long postId;
    private String img;
    private Boolean isRead;
    private FieldValue timestamp;
    public NotificationDTO() {
        this.isRead = false;
    }
}
