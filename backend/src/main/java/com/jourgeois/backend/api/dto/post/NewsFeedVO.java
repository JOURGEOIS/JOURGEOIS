package com.jourgeois.backend.api.dto.post;

import java.time.LocalDateTime;

public interface NewsFeedVO {
    // 게시글 공통
    LocalDateTime getCreateTime();
    LocalDateTime getUpdateTime();
    Integer getIsUpdated();
    Long getPid();
    String getType();
    Long getWriter();
    String getNickname();
    String getProfileImg();
    Integer getIsSuperCustomCocktail();
    String getCocktailTitle();
    String getPostImg();
    String getDescription();
    Long getFollowerCount();
    Long getReviewCount();
    Long getLikeCount();

    Long getBaseCocktailId();

    String getBaseCocktailName();
}
