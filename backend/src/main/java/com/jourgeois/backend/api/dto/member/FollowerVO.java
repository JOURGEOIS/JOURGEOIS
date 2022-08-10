package com.jourgeois.backend.api.dto.member;

public interface FollowerVO {
    Long getUid();
    String getNickname();
    String getProfileImg();
    Integer getIsFollowed();
    String getIntroduce();
}
