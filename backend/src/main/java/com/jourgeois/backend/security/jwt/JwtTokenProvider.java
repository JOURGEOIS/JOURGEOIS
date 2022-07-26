package com.jourgeois.backend.security.jwt;

import com.jourgeois.backend.repository.auth.RefreshTokenRepository;
import com.jourgeois.backend.security.MyUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {

    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final String secretKey;
    private final long tokenValidityInMs;
    private final long refreshTokenValidityInMs;

    // 초기 setting (환경변수는 application에)
    JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey,
                            @Value("${jwt.token-validity-in-sec}") long tokenValidity,
                            @Value("${jwt.refresh-token-validity-in-sec}") long refreshTokenValidity,
                            MyUserDetailsService myUserDetailsService,
                            RefreshTokenRepository refreshTokenRepository){

        this.secretKey = secretKey;
        this.tokenValidityInMs = tokenValidity * 1000 * 60;
        this.refreshTokenValidityInMs = refreshTokenValidity * 1000;
        this.myUserDetailsService = myUserDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private Key key;

    // init()
    @Override
    public void afterPropertiesSet() throws Exception {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
        // https://budnamu.tistory.com/entry/JWT 참고
    }

    public String createAccessToken(Authentication authentication) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now) // 발행시간
                .signWith(key, SignatureAlgorithm.HS512) // 암호화
                .setExpiration(validity) // 만료
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        System.out.println("claims.getSubject(): " + claims.getSubject());
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(claims.getSubject());
        System.out.println("userDetails" + userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e){
            // 만료된 경우에는 refresh token을 확인하기 위해
            System.out.println("만료된 토큰입니다.");
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("jwtException : {}", e);
            throw e;
        }
    }

    public String createRefreshToken(Authentication authentication){
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityInMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

}