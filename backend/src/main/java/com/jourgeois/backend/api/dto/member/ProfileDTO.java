package com.jourgeois.backend.api.dto.member;


import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

//@Builder
public class ProfileDTO {
    private Long uid;
    private String email;
    private String name;
    private String nickname;
    private String profileImg;
    private MultipartFile profileLink;
    private String introduce;

    public ProfileDTO(Long uid, String nickname, String profileImg) {
        this.uid = uid;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public MultipartFile getProfileLink() {
        return profileLink;
    }

    public void setProfileLink(MultipartFile profileLink) {
        this.profileLink = profileLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public ProfileDTO(){}

    public ProfileDTO(Long uid, String email, String name, String nickname, String profileImg, String introduce) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }

//    public ProfileDTO(Long uid, String nickname, String profileImg){}
}
