package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.TokenResponseDTO;
import com.jourgeois.backend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    private  final TokenService tokenService;

    @Autowired
    AuthController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public TokenResponseDTO reissueAccessToken(@RequestBody Map<String, String> token){
        System.out.println("token: " + token.get("token"));
        return tokenService.reissueAccessToken(token.get("token"));
    }
}
