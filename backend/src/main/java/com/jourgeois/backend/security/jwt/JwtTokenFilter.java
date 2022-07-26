package com.jourgeois.backend.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";

    private JwtTokenProvider jwtTokenProvider;

    JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    // controller 들어가기 전 인증 확인
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request, AUTHORIZATION_HEADER);
        try{
            if ( jwt != null && jwtTokenProvider.validateToken(jwt)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                if(request.getParameter("email") != null && request.getParameter("email").equals(authentication.getName())){
                    System.out.println("TESTTESTTEST " + authentication.getName() + " " + request.getParameter("email"));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
                }else {
                    System.out.println("토큰과 이메일이 같지 않음");
                    request.setAttribute("exception", "Exception");
                }
            }
        } catch(ExpiredJwtException e){
            request.setAttribute("exception", "ExpiredJwt");
            log.info("ExpiredJwtException {}", e.getMessage());
        } catch(JwtException | IllegalArgumentException e){
            request.setAttribute("exception", "Exception");
            log.info("jwtException {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    // token 검증
    private String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith("Bearer-")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
