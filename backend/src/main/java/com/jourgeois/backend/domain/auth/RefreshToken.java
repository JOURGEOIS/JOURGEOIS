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

    private String email;
    private String token;


    private RefreshToken(String id, String token) {
        this.email = id;
        this.token = token;
    }

    public static RefreshToken createToken(String userId, String token){
        return new RefreshToken(userId, token);
    }

    public void changeToken(String token) {
        this.token = token;
    }
}
