package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/member")
//@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {this.memberService = memberService;}

    @GetMapping(value = "/login")
    public String loginMember(){
        System.out.println("login 입니다");
        return "";
    }

    @GetMapping(value = "/register")
    public ResponseEntity<Member> memberRegister(@RequestParam("id") String id){
        System.out.println("안녕하세요");
//        Member m = new Member(
//                "jsznawa@Naver.com", "1234", "전승준", "paasasd",
//                "1997-12-26", "a.img", "안녕하세요 전 승준입니다.");
        Member m = new Member("seona", "123");
        try {
            memberService.createUser(m);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Member>(HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestParam String id,
                                     @RequestParam String pw) {
        System.out.println(id + " " + pw);
        Map<String, Object> data = new HashMap<>();
        data.put("token", memberService.signIn(id,pw));
        data.put("userInfo", memberService.findUserInfo(id));
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public HttpStatus logout(@RequestParam String email){
        System.out.println(email + " ");
        memberService.logout(email);
        return HttpStatus.OK;
    }


    @PutMapping("/auth/profile")
    public ResponseEntity changeProfile(@RequestBody ProfileDto profileDto){
        Map<String, Boolean> data = new HashMap<>();
        try {
            memberService.changeProfile(profileDto);
            data.put("success", true);
            return new ResponseEntity(data, HttpStatus.CREATED);
        }catch (Exception e){
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.CREATED);
        }
    }
}
