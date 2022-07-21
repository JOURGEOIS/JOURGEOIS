package com.jourgeois.backend.controller;

import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.model.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    private MemberService memberService;
    
    @GetMapping(value = "/login")
    public String loginMember(){
        System.out.println("login 입니다");
        return "";
    }

    @GetMapping(value = "/register")
    public ResponseEntity<Member> memberRegister(@RequestParam("id") String id){
        System.out.println("안녕하세요");
        Member m = new Member(id, "1234", "전승준", "paasasd",
                "jsznawa@Naver.com", "1997-12-26", "a.img", "안녕하세요 전 승준입니다.");
        try {
            memberService.createUser(m);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Member>(HttpStatus.OK);
    }
}
