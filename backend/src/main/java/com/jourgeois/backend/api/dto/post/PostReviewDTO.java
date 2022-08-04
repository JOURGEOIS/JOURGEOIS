package com.jourgeois.backend.api.dto.post;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostReviewDTO {
    Long pr_id;
    Long uid;
    Long p_id;
    String review;
}
