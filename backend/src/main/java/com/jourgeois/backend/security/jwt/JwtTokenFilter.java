package com.jourgeois.backend.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@Slf4j

public class JwtTokenFilter extends OncePerRequestFilter{
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";

    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtTokenProvider.resolveToken(request, AUTHORIZATION_HEADER);
        try{
            if ( jwt != null && jwtTokenProvider.validateToken(jwt)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("uid", (Object) authentication.getName().toString());
                log.info("set Authentication to security context for '{}', uri: {}", authentication.getName());
            }else {
                request.setAttribute("exception", "Exception");
            }
//            }
        } catch(ExpiredJwtException e){
            request.setAttribute("exception", "ExpiredJwt");
            log.info("ExpiredJwtException {}", e.getMessage());
        } catch(JwtException | IllegalArgumentException e){
            request.setAttribute("exception", "Exception");
            log.info("jwtException {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
