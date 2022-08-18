package com.jourgeois.backend.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 401
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if(exception.equals("ExpiredJwt")){
//            System.out.println("만료된 인증키");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else if(exception.equals("Exception")){
//            System.out.println("잘못된 접근");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        else {
//            System.out.println("잘못된 접근");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
        }
    }
}
