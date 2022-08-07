package com.jourgeois.backend.api.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter@Builder
public class FollowerDTO {
    private Long uid;
    private String nickname;
    private String profileImg;
    private Integer isFollowed;
    private String introduce;
}
