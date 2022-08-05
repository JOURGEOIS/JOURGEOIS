package com.jourgeois.backend.api.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NewsFeedDTO {
    // 게시글 공통
    LocalDateTime createTime;
    LocalDateTime updateTime;
    Integer isUpdated;
    Long pid;
    String type;
    Long writer;
    String nickname;
    String profileImg;
    Integer isSuperCustomCocktail;
    String cocktailTitle;
    String postImg;
    String description;
    Long followerCount;
    Long reviewCount;
    Long likeCount;

    Long baseCocktailId;

    String baseCocktailName;
}
