package com.jourgeois.backend.domain.member;

import com.jourgeois.backend.domain.cocktail.CocktailBookmark;
import com.jourgeois.backend.domain.cocktail.CocktailComment;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.domain.post.PostBookmark;
import com.jourgeois.backend.domain.post.PostReview;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
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
    private String profileImg ="profile/default/1.png";

    private String introduce;
    @CreatedDate
    private java.util.Date creationDate;
    private String roles = "ROLE_USER";

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @OneToMany(mappedBy = "memberId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CocktailBookmark> cocktailBookmarks;

    @OneToMany(mappedBy = "memberId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostBookmark> postBookmarks;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CocktailComment> cocktailReviews;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostReview> postReviews;

    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Follow> follower;

    @OneToMany(mappedBy = "to", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Follow> followee;
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

    @Builder
    public Member(Long uid) {
        this.uid = uid;
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
                ", roles='" + roles + '\''; //+
//                ", posts=" + posts +
//                ", cocktailBookmarks=" + cocktailBookmarks +
//                ", postBookmarks=" + postBookmarks +
//                ", cocktailReviews=" + cocktailReviews +
//                ", postReviews=" + postReviews +
//                '}';
    }
}
