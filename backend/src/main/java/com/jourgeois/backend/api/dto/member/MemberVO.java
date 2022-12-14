package com.jourgeois.backend.api.dto.member;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface MemberVO {
    Long getUid();
    String getEmail();
    String getName();
    String getNickname();
    String getProfileImg();
    MultipartFile getProfileLink();
    String getIntroduce();
    Integer getFollowerCnt();
    Integer getFollowingCnt();
    Integer getPostCnt();
    LocalDateTime getCreateTime();
    String getPostImg();
    String getDescription();
    Integer getIsPrivate();
    Integer getLikes();
    Integer getIlike();

    Integer getIsFollowed();

    Long getPostId();
    String getBaseCocktail();
    String getTitle();
    String getIngredients();
}