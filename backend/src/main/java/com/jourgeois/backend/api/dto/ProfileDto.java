package com.jourgeois.backend.api.dto;

public class ProfileDto {
    private String email;
    private String nickname;
    private String profileImg;
    private String introduce;

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
                "nickname='" + nickname + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
