package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.auth.TokenResponseDTO;
import com.jourgeois.backend.domain.auth.RefreshToken;
import com.jourgeois.backend.repository.auth.RefreshTokenRepository;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    TokenService(JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository){
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public TokenResponseDTO reissueAccessToken(String token) {
        String resolveToken = resolveToken(token);

        //토큰 검증 메서드
        //실패시 jwtTokenProvider.validateToken(resolveToken) 에서 exception을 리턴함
        jwtTokenProvider.validateToken(resolveToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(resolveToken);
        // 디비에 있는게 맞는지 확인
        RefreshToken findTokenEntity = refreshTokenRepository.findByUid(Long.parseLong(authentication.getName()))
                .orElseThrow(()-> new RuntimeException("not find refresh Token"));

        // 토큰이 같은지 확인
        if(!resolveToken.equals(findTokenEntity.getToken())){
            throw new RuntimeException("not equals refresh token");
        }

        // 재발행해서 저장
        String newToken = jwtTokenProvider.createRefreshToken(authentication);
        findTokenEntity.changeToken(newToken);

        // accessToken과 refreshToken 모두 재발행
        return TokenResponseDTO.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+newToken)
                .build();
    }


    //token 앞에 "Bearer-" 제거
    private String resolveToken(String token){
        if(token.startsWith("Bearer-"))
            return token.substring(7);
        throw new RuntimeException("not valid refresh token !!");
    }
}
