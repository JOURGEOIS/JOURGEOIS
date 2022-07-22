package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.PasswordChangeForm;
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

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@RequestBody Member member){
        System.out.println("===========================");
        System.out.println(member.toString());
        System.out.println("===========================");
        Map<String, Boolean> data = new HashMap<>();
        // 이메일 중복, 닉네임 중복 재 검사
        boolean flag = memberService.checkEmail(member.getEmail()) && memberService.checkNickname(member.getNickname());

        try {
            if(flag) {
                boolean res = memberService.signUp(member);
                data.put("success", res);
            } else {
                data.put("success", false);
            }
        } catch (Exception e){
            e.printStackTrace();
            data.put("success", false);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    //이메일 중복 체크 메소드
    @GetMapping(value = "/signup/checkEmail")
    public @ResponseBody ResponseEntity<?> checkEmail(@RequestParam String email){
        System.out.println("[/signup/emailCheck] email = " + email);
        Map<String, Boolean> data = new HashMap<>();

        try {
            boolean res = memberService.checkEmail(email);
            data.put("available", res);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(data, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/signup/checkNickname")
    public @ResponseBody ResponseEntity<?> checkNickname(@RequestParam String nickname){
        Map<String, Boolean> data = new HashMap<>();

        try {
            boolean res = memberService.checkNickname(nickname);
            data.put("available", res);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(data, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                     @RequestParam String password) {
        System.out.println(email + " " + password);
        Map<String, Object> data = new HashMap<>();
        data.put("token", memberService.login(email ,password));
        data.put("userInfo", memberService.findUserInfo(email));
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public HttpStatus logout(@RequestParam String email){
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
        }catch (Exception e) {
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
