package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.TokenResponseDto;
import com.jourgeois.backend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {
    private  final TokenService tokenService;

    @Autowired
    AuthController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/token")
    public TokenResponseDto reissueAccessToken(@RequestHeader String refreshToken){
        System.out.println("token: " + refreshToken);
        return tokenService.reissueAccessToken(refreshToken);
    }
}
