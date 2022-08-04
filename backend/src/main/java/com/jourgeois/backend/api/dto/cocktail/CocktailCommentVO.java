package com.jourgeois.backend.api.dto.cocktail;


import java.time.LocalDateTime;

public interface CocktailCommentVO {
    Long getCommentId();
    Long getCocktailId();
    Long getUserId();

    String getNickname();
    String getProfileImg();
    LocalDateTime getCreatedDate();
    LocalDateTime getModifiedDate();
    String getComment();
}
