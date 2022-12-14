package com.jourgeois.backend.api.dto.member;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

//@Builder
@Getter @Setter
@ToString
@NoArgsConstructor
public class MemberDTO {
    private Long uid;
    private String email;
    private String name;
    private String nickname;
    private String profileImg;
    private MultipartFile profileLink;
    private String introduce;
    private Integer followerCnt;
    private Integer followingCnt;
    private Integer postCnt;
    private Integer isPrivate;
    private Integer likes;
    private Integer iLike;
    private Long postId;
    private String baseCocktail;
    private String title;
    private String ingredients;

    @Builder
    public MemberDTO(Long uid, String email, String nickname, String profileImg, String introduce, Integer followerCnt,
                     Integer followingCnt, Integer postCnt, Integer isPrivate, Integer likes, Integer iLike, Long postId, String baseCocktail, String title, String ingredients) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduce = introduce;
        this.followerCnt = followerCnt;
        this.followingCnt = followingCnt;
        this.postCnt = postCnt;
        this.isPrivate = isPrivate;
        this.likes = likes;
        this.iLike = iLike;
        this.postId = postId;
        this.baseCocktail = baseCocktail;
        this.title = title;
        this.ingredients = ingredients;
    }

    public MemberDTO(Long uid, String email, String name, String nickname, String profileImg, String introduce) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }

    public MemberDTO(Long uid, String nickname, String profileImg) {
        this.uid = uid;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }

}
