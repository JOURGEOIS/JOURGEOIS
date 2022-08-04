package com.jourgeois.backend.api.dto.post;

import java.time.LocalDateTime;
public interface PostReviewResponseVO {
    LocalDateTime getCreateTime();
    LocalDateTime getUpdateTime();
    Integer getIsUpdated();
    String getReview();
    Long getUid();
    String getNickname();
    String getProfileImg();
}
