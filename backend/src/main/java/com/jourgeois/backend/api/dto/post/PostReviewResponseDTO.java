package com.jourgeois.backend.api.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class PostReviewResponseDTO {
    LocalDateTime createTime;
    LocalDateTime updateTime;
    Integer isUpdated;
    String review;
    Long uid;
    String nickname;
    String profileImg;
    Integer reviewCount;
    Integer isMine;
    Long pr_id;
}
