package com.jourgeois.backend.domain.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "refresh_token_id")
    private Long id;

//    @OneToOne
//    @JoinColumn
    private Long uid;
    private String token;


    private RefreshToken(Long uid, String token) {
        this.uid = uid;
        this.token = token;
    }

    public static RefreshToken createToken(Long uid, String token){
        return new RefreshToken(uid, token);
    }

    public void changeToken(String token) {
        this.token = token;
    }
}
