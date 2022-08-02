package com.jourgeois.backend.api.dto;


import java.time.LocalDateTime;

public interface CocktailCommentVO {
    Long getId();
    Long getCocktailId();
    Long getUserId();
    LocalDateTime getCreatedDate();
    LocalDateTime getModifiedDate();
    String getReview();
}
