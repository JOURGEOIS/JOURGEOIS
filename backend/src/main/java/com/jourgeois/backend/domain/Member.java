package com.jourgeois.backend.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long uid;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthday;
    @ColumnDefault("default/1.png")
    private String profileImg;
    private String introduce;
    @CreatedDate
    private java.util.Date creationDate;
    @ColumnDefault("ROLE_USER")
    private String roles;

    @Builder
    public Member(String email, String password, String name, String nickname,  String birthday, String profileImg, String introduce) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Member{" +
                "uid=" + uid +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", introduce='" + introduce + '\'' +
                ", creationDate=" + creationDate +
                ", roles='" + roles + '\'' +
                '}';
    }

    public List<String> getRoleList() {
        if (roles.length() > 0) {
            System.out.println("roles " + roles.split(",").toString());
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public Member(String email, String pw) {
        this.email = email;
        this.password = pw;
        this.roles = "ROLE_USER";
    }

//    public static Member testCreate(String userId, String pw) {
//        return Member.builder()
//                .userId(userId)
//                .pw(pw)
//                .build();
//    }
}
