package com.jourgeois.backend.api.dto;


import java.time.LocalDateTime;

public interface CocktailCommentVO {
    Long getCommentId();
    Long getCocktailId();
    Long getUserId();

    String getNickname();
    String getProfileImg();
    Integer getLikes();
    LocalDateTime getCreatedDate();
    LocalDateTime getModifiedDate();
    String getComment();
}
