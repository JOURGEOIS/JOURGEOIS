package com.jourgeois.backend.api.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostReviewDTO {
    Long uid;
    Long p_id;
    String review;
}
