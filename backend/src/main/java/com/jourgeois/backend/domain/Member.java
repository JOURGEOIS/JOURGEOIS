package com.jourgeois.backend.domain;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(nullable = false, name = "UID")
    private String uid;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NAME")
    private String name;
    @Column(name = "NICKNAME")
    private String nickname;
    @Column(name = "BIRTHDAY")
    private String birthday;
    @Column(name = "PROFILE_IMG")
    private String profileImg;
    @Column(name = "INTRODUCE")
    private String introduce;
//    private String createDate;

    public Member(String uid, String password, String name, String nickname, String email, String birthday, String profileImg, String introduce) {
        this.uid = uid;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.birthday = birthday;
        this.profileImg = profileImg;
        this.introduce = introduce;
//        this.createDate = createDate;
    }

    public Member() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
//
//    public String getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        this.createDate = createDate;
//    }

    @Override
    public String toString() {
        return "Member{" +
                "uid='" + uid + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }
}
