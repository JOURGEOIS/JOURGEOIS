package com.jourgeois.backend.api.dto.notification;

import com.jourgeois.backend.api.dto.post.PostMetaDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class NotificationResponseDTO {
    private NotificationDTO notification;
    private OpponentDTO opponent;
    private PostMetaDTO postMetaInfo;
    private Long lastSize;
}
