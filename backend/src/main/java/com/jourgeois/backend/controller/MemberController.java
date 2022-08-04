package com.jourgeois.backend.controller;

import com.jourgeois.backend.api.dto.member.ProfileDTO;
import com.jourgeois.backend.api.dto.member.PasswordChangeForm;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/member")
//@RequiredArgsConstructor
public class MemberController {
    public static final String AUTHORIZATION_HEADER = "Authorization";
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginForm) {
        String email = loginForm.get("email");
        String password = loginForm.get("password");
        System.out.println(email + " " + password);

        Map<String, Object> data = new HashMap<>();
        UserDetails userDetails = memberService.loginUser(email, password);
        data.put("token", memberService.createToken(userDetails));
        data.put("userInfo", memberService.findUserInfo(Long.valueOf(userDetails.getUsername())));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/auth/logout")
    public HttpStatus logout(HttpServletRequest  request){
        memberService.logout(Long.parseLong((String) request.getAttribute("uid")));
        return HttpStatus.OK;
    }

    @DeleteMapping("/auth/signOut")
    public ResponseEntity signOut(HttpServletRequest  request, @RequestBody Map<String, String> user) {
        try {
            memberService.signOut(Long.valueOf((String) request.getAttribute("uid")), user.get("email"));
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @PutMapping("/auth/profile")
    public ResponseEntity changeProfile(HttpServletRequest request, @ModelAttribute ProfileDTO profileDto){
        Map<String, Boolean> data = new HashMap<>();

        try {
            profileDto.setUid(Long.valueOf(((String) request.getAttribute("uid"))));
            memberService.changeProfile(profileDto);
            return new ResponseEntity(memberService.findUserInfo(profileDto.getUid()), HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 이미지 파일 업로드
    @PostMapping("/auth/profile")
    public ResponseEntity profileImageTempStorage(HttpServletRequest request, @ModelAttribute ProfileDTO profileDto){
        try{
            Map<String, String> data = new HashMap<>();
            profileDto.setUid(Long.parseLong((String) request.getAttribute("uid")));
            data.put("url", "http://13.209.206.237/img/" + memberService.ProfileImageLocalUpload(profileDto));
            return new ResponseEntity(data, HttpStatus.CREATED);
        }catch (Exception e) {
            Map<String, Boolean> data = new HashMap<>();
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 비밀번호 변경
    @PutMapping("/auth/password")
    public ResponseEntity changePassword(HttpServletRequest request, @RequestBody PasswordChangeForm passwordChangeForm) {
        Map<String, Boolean> data = new HashMap<>();

        try {
            passwordChangeForm.setUid(Long.parseLong((String) request.getAttribute("uid")));
            memberService.changePassword(passwordChangeForm);
            data.put("success", true);
            return new ResponseEntity(data, HttpStatus.CREATED);
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
            passwordChangeForm.setUid(Long.parseLong((String) request.getAttribute("uid")));
            boolean result = memberService.checkPassword(passwordChangeForm);
            data.put("success", result);
            return result ? new ResponseEntity(data, HttpStatus.CREATED) : new ResponseEntity(data, HttpStatus.CREATED);
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

    // 팔로우
    @PostMapping("/follow")
    public ResponseEntity followMember(@RequestBody Map<String, Long> followRequest) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            memberService.followUser(followRequest);
            data.put("success", true);
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch(JpaObjectRetrievalFailureException e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 나를 팔로우하는 사람 목록 보기
    @GetMapping("/follower")
    public ResponseEntity getFollowerAll(@RequestParam Map<String, String> request,
                                         @PageableDefault(size=10, page = 0) Pageable pageable) {
        Map<String, String> data = new HashMap<>();
        try {
            return new ResponseEntity(memberService.getFollowerAll(request, pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "잘못된 인풋");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
