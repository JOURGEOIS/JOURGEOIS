package com.jourgeois.backend.domain.auth;

import com.jourgeois.backend.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private Member memberid;
    private String token;


    private RefreshToken(Member uid, String token) {
        this.memberid = uid;
        this.token = token;
    }

    public static RefreshToken createToken(Member uid, String token){
        return new RefreshToken(uid, token);
    }

    public void changeToken(String token) {
        this.token = token;
    }
}
