package com.jourgeois.backend.controller;

import com.jourgeois.backend.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    MemberService memberService;

    @GetMapping(value = "/email")
    public String authEmail(){
        System.out.println("Controller - /auth/email 입니다.");
//        memberService.createUser();
        return "";
    }
}
