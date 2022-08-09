package com.jourgeois.backend.api.dto.member;

import org.springframework.web.multipart.MultipartFile;

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
    String getCreateTime();
    String getPostImg();
    String getDescription();
}