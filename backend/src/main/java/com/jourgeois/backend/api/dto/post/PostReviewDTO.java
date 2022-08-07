package com.jourgeois.backend.api.dto.post;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostReviewDTO {
    Long postReviewId;
    Long uid;
    Long postId;
    String review;
}
