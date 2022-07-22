package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.PasswordChangeForm;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.util.S3Util;
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

    private final MemberService memberService;
    private final S3Util s3Uploader;

    @Autowired
    MemberController(MemberService memberService, S3Util s3Uploader) {
        this.memberService = memberService;
        this.s3Uploader = s3Uploader;
    }

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
        Member m = new Member("seona@naver.com", "123");
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
        memberService.logout(email);
        return HttpStatus.OK;
    }

    @PutMapping("/profile")
    public ResponseEntity changeProfile(@ModelAttribute ProfileDto profileDto){
        Map<String, Boolean> data = new HashMap<>();
        try {
            memberService.changeProfile(profileDto);
            data.put("success", true);
            return new ResponseEntity(data, HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.CREATED);
        }
    }

    // 비밀번호 변경
    @PutMapping("/auth/password")
    public ResponseEntity changePassword(@RequestBody PasswordChangeForm passwordChangeForm) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            memberService.changePassword(passwordChangeForm);
            data.put("success", true);
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.CREATED);
        }
    }

    // 비밀번호 찾기
    @PutMapping("/password")
    public ResponseEntity findPassword(@RequestBody PasswordChangeForm passwordChangeForm) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            memberService.findPassword(passwordChangeForm);
            data.put("success", true);
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.CREATED);
        }
    }
}
