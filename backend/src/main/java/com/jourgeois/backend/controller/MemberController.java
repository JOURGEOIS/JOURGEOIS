package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.ProfileDto;
import com.jourgeois.backend.api.dto.PasswordChangeForm;
import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/member")
//@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final S3Util s3Uploader;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberController(MemberService memberService, S3Util s3Uploader,JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.s3Uploader = s3Uploader;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/signUp")
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

    // 선아.... login시에 pswd 문제로 request body로 바꿨엉..
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginForm) {
        String email = loginForm.get("email");
        String password = loginForm.get("password");
        System.out.println(email + " " + password);
        Map<String, Object> data = new HashMap<>();
        data.put("token", memberService.login(email ,password));
        data.put("userInfo", memberService.findUserInfo(email));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public HttpStatus logout(@RequestParam String email){
        memberService.logout(email);
        return HttpStatus.OK;
    }

    @DeleteMapping("/auth/signOut")
    public ResponseEntity signOut(HttpServletRequest request, @RequestBody Map<String, String> user) {
        try {
            String jwt = jwtTokenProvider.resolveToken((HttpServletRequest)request, "Authorization");
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            if(authentication.getName().equals(user.get("email"))) {
                System.out.println("같은 회원!");
                memberService.signOut(user.get("email"));
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }else {
                System.out.println("다른 회원!");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); //403 error
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PutMapping("/auth/profile")
    public ResponseEntity changeProfile(HttpServletRequest request, @ModelAttribute ProfileDto profileDto){
        Map<String, Boolean> data = new HashMap<>();
        try {
            String jwt = jwtTokenProvider.resolveToken((HttpServletRequest)request, "Authorization");
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            if(authentication.getName().equals(profileDto.getEmail())) {
                System.out.println("같은 회원!");
                memberService.changeProfile(profileDto);
                return new ResponseEntity(memberService.findUserInfo(profileDto.getEmail()), HttpStatus.CREATED);
            }else {
                System.out.println("다른 회원!");
                data.put("success", false);
                return new ResponseEntity(data, HttpStatus.FORBIDDEN);
            }
        }catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 비밀번호 변경
    @PutMapping("/auth/password")
    public ResponseEntity changePassword(HttpServletRequest request, @RequestBody PasswordChangeForm passwordChangeForm) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            String jwt = jwtTokenProvider.resolveToken((HttpServletRequest)request, "Authorization");
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            if(authentication.getName().equals(passwordChangeForm.getEmail())) {
                System.out.println("같은 회원!");
                memberService.changePassword(passwordChangeForm);
                data.put("success", true);
                return new ResponseEntity(data, HttpStatus.CREATED);
            }else {
                System.out.println("다른 회원!");
                data.put("success", false);
                return new ResponseEntity(data, HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            data.put("회원 정보 없음", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/password")
    public ResponseEntity checkUser(@RequestParam String email, String userName) {
        Map<String, Boolean> data = new HashMap<>();
        boolean isPresent = memberService.checkUser(email, userName);
        data.put("success", isPresent);
        return isPresent ? new ResponseEntity(data, HttpStatus.ACCEPTED) : new ResponseEntity(data, HttpStatus.ACCEPTED);
    }

    @PostMapping("/auth/password")
    public ResponseEntity checkPassword(HttpServletRequest request, @RequestBody PasswordChangeForm passwordChangeForm) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            String jwt = jwtTokenProvider.resolveToken((HttpServletRequest)request, "Authorization");
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            if(authentication.getName().equals(passwordChangeForm.getEmail())) {
                System.out.println("같은 회원!");
                boolean result = memberService.checkPassword(passwordChangeForm);
                data.put("success", result);
                return result ? new ResponseEntity(data, HttpStatus.CREATED) : new ResponseEntity(data, HttpStatus.CREATED);
            }else {
                System.out.println("다른 회원!");
                data.put("success", false);
                return new ResponseEntity(data, HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
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
        } catch (IllegalArgumentException e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 이미지 파일 업로드
    @PostMapping("/test")
    public ResponseEntity testest(@RequestBody MultipartFile multipartFile) throws IOException {
        try{
            Map<String, String> data = new HashMap<>();
            // 이메일로 변경 필요
            data.put("url", "http://localhost:8080/img/"+s3Uploader.localUpload(multipartFile, "test"));
            return new ResponseEntity(data, HttpStatus.CREATED);
        }catch (Exception e) {
            Map<String, Boolean> data = new HashMap<>();
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
