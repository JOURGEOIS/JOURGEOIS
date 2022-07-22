package com.jourgeois.backend.api.dto;


import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public class ProfileDto {
    private String email;
    private String name;
    private String nickname;
    private String profileImg;
    private MultipartFile profileLink;
    private String introduce;

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

    @Override
    public String toString() {
        return "ProfileDto{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", profileLink=" + profileLink +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
