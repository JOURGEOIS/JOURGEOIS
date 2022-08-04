package com.jourgeois.backend.api.dto.post;

import org.joda.time.DateTime;

import java.time.LocalDateTime;

public interface CustomCocktailVO {
    Long getUid();
    String getNickname();
    String getProfileImg();
    String getPostIngredients();
    String getPostRecipe();
    Long getPostId();
    String getPostImg();
    String getPostTitle();
    LocalDateTime getPostCreateTime();
    LocalDateTime getPostLastUpdateTime();
    String getPostDescription();
    Integer getPostCount();

}
