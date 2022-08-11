package com.jourgeois.backend.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.api.dto.member.PasswordChangeForm;
import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import com.jourgeois.backend.service.MemberService;
import com.jourgeois.backend.service.NotificationService;
import com.jourgeois.backend.socialLogin.SocialLoginConfigUtils;
import com.jourgeois.backend.socialLogin.GoogleLoginDTO;
import com.jourgeois.backend.socialLogin.GoogleLoginRequest;
import com.jourgeois.backend.socialLogin.GoogleLoginResponse;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/member")
//@RequiredArgsConstructor
public class MemberController {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final MemberService memberService;
    private final NotificationService notificationService;
    private final S3Util s3Uploader;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberController(MemberService memberService, NotificationService notificationService, S3Util s3Uploader, JwtTokenProvider jwtTokenProvider, SocialLoginConfigUtils configUtils) {
        this.memberService = memberService;
        this.notificationService = notificationService;
        this.s3Uploader = s3Uploader;
        this.jwtTokenProvider = jwtTokenProvider;
        this.configUtils = configUtils;
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

    /*
    =================================================================== google login
     */
    private final SocialLoginConfigUtils configUtils;

    @GetMapping(value = "/login/google")
    public ResponseEntity<Object> moveGoogleInitUrl() {
        String authUrl = configUtils.googleInitUrl();
        try {
            URI redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/login/google/redirect")
    public ResponseEntity<?> redirectGoogleLogin(@RequestParam(value = "code") String authCode) {
        // HTTP 통신을 위해 RestTemplate 활용
        System.out.println("Google authCde :" + authCode);

        RestTemplate restTemplate = new RestTemplate();
        GoogleLoginRequest requestParams = GoogleLoginRequest.builder()
                .clientId(configUtils.getGoogleClientId())
                .clientSecret(configUtils.getGoogleSecret())
                .code(authCode)
                .redirectUri(configUtils.getGoogleRedirectUri())
                .grantType("authorization_code")
                .build();

        try {
            // Http Header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccessControlAllowOrigin("*");
            HttpEntity<GoogleLoginRequest> httpRequestEntity = new HttpEntity<>(requestParams, headers);
            ResponseEntity<String> apiResponseJson = restTemplate.postForEntity(configUtils.getGoogleAuthUrl() + "/token", httpRequestEntity, String.class);

            // ObjectMapper를 통해 String to Object로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL이 아닌 값만 응답받기(NULL인 경우는 생략)
            GoogleLoginResponse googleLoginResponse = objectMapper.readValue(apiResponseJson.getBody(), new TypeReference<GoogleLoginResponse>() {});

            // 사용자의 정보는 JWT Token으로 저장되어 있고, Id_Token에 값을 저장한다.
            String jwtToken = googleLoginResponse.getIdToken();

            // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
            String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo").queryParam("id_token", jwtToken).toUriString();

            String resultJson = restTemplate.getForObject(requestUrl, String.class);

            if(resultJson != null) {
                GoogleLoginDTO googleLoginInfo = objectMapper.readValue(resultJson, new TypeReference<GoogleLoginDTO>() {});
                googleLoginInfo.setEmail("google/"+googleLoginInfo.getEmail());


                Map<String, Object> data = new HashMap<>();
                UserDetails userDetails = memberService.loginUser(googleLoginInfo);

                data.put("token", memberService.createToken(userDetails));
                data.put("userInfo", memberService.findUserInfo(Long.valueOf(userDetails.getUsername())));

                return ResponseEntity.ok().body(data);
            }
            else {
                throw new Exception("Google OAuth failed!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/login/kakao")
    public ResponseEntity<?> moveKakaoInitUrl() {
        String authUrl = configUtils.kakaoInitUrl();
        try {
            URI redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/login/kakao/redirect")
    public ResponseEntity<?> redirectKakaoLogin(@RequestParam String code) {

        System.out.println("Kakao Code : " + code);
        try {
            String accessToken = memberService.getSocialAccessToken(code, "kakao");
            Map<String, Object> kakaoUserInfo = memberService.getSocialUserInfo(accessToken, "kakao");
            UserDetails userDetails = memberService.loginSocialUser(kakaoUserInfo, "kakao");
            Map<String, Object> data = new HashMap<>();

            data.put("token", memberService.createToken(userDetails));
            data.put("userInfo", memberService.findUserInfo(Long.valueOf(userDetails.getUsername())));

            return ResponseEntity.ok().body(data);

        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RequestMapping(value = "/login/naver", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> moveNaverInitUrl() {
        String authUrl = configUtils.naverInitUrl();
        try {
            URI redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/login/naver/redirect")
    public ResponseEntity<?> redirectNaverLogin(@RequestParam String code) {

        System.out.println("Naver Code : " + code);
        try {
            String accessToken = memberService.getSocialAccessToken(code, "naver");
            Map<String, Object> naverUserInfo = memberService.getSocialUserInfo(accessToken, "naver");
            System.out.println(naverUserInfo.get("email"));
            UserDetails userDetails = memberService.loginSocialUser(naverUserInfo, "naver");

            Map<String, Object> data = new HashMap<>();

            data.put("token", memberService.createToken(userDetails));
            data.put("userInfo", memberService.findUserInfo(Long.valueOf(userDetails.getUsername())));

            return ResponseEntity.ok().body(data);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
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
    public ResponseEntity changeProfile(HttpServletRequest request, @ModelAttribute MemberDTO memberDto){
        Map<String, Boolean> data = new HashMap<>();

        try {
            memberDto.setUid(Long.valueOf(((String) request.getAttribute("uid"))));
            memberService.changeProfile(memberDto);
            return new ResponseEntity(memberService.findUserInfo(memberDto.getUid()), HttpStatus.CREATED);
        }catch (Exception e) {
            System.out.println(e);
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    // 이미지 파일 업로드
    @PostMapping("/auth/profile")
    public ResponseEntity profileImageTempStorage(HttpServletRequest request, @ModelAttribute MemberDTO memberDto){
        try{
            Map<String, String> data = new HashMap<>();
            memberDto.setUid(Long.parseLong((String) request.getAttribute("uid")));
            data.put("url", "https://www.jourgeois.com/img/" + memberService.ProfileImageLocalUpload(memberDto));
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
    @PostMapping("/auth/follow")
    public ResponseEntity followMember(HttpServletRequest request, @RequestBody Map<String, Long> followRequest) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            Long from = Long.valueOf((String) request.getAttribute("uid"));
            Long to = followRequest.get("to");

            if(to == from) {
                data.put("success", false);
                return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
            }

            Follow follow = memberService.followUser(from, to);
            data.put("success", notificationService.followNotification(follow.getTo(), follow.getFrom()));
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch(JpaObjectRetrievalFailureException e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 다른 유저를 팔로우하는 사람 목록 보기
    @GetMapping("/auth/follower")
    public ResponseEntity getFollowerAll(HttpServletRequest request,
                                         @RequestParam Long uid,
                                         @PageableDefault(size=10, page = 0) Pageable pageable) {
        Map<String, String> data = new HashMap<>();
        try {
            Long me = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(memberService.getFollowerAll(uid, me, pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "uid 형식 오류");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 다른 유저가 팔로우하는 살마 목록 보기
    @GetMapping("/auth/followee")
    public ResponseEntity getFolloweeAll(HttpServletRequest request,
                                         @RequestParam Long uid,
                                         @PageableDefault(size=10, page = 0) Pageable pageable) {
        Map<String, String> data = new HashMap<>();
        try {
            Long me = Long.valueOf((String) request.getAttribute("uid"));
            return new ResponseEntity(memberService.getFolloweeAll(uid, me, pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "uid 형식 오류");
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 나를 팔로우하는 사람 목록 보기
    @GetMapping("/auth/my-follower")
    public ResponseEntity getMyFollowerAll(HttpServletRequest request, @PageableDefault(size=10, page = 0) Pageable pageable) {
        Map<String, String> data = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            Long me = uid;
            return new ResponseEntity(memberService.getFollowerAll(uid, me, pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "uid가 이상해요.");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 내가 팔로우하는 살마 목록 보기
    @GetMapping("/auth/my-followee")
    public ResponseEntity getMyFolloweeAll(HttpServletRequest request, @PageableDefault(size=10, page = 0) Pageable pageable) {
        Map<String, String> data = new HashMap<>();
        try {
            Long uid = Long.valueOf((String) request.getAttribute("uid"));
            Long me = uid;
            return new ResponseEntity(memberService.getFolloweeAll(uid, me, pageable), HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println(e);
            data.put("fail", "uid가 이상해요.");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println(e);
            data.put("fail", "오류 발생");
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 팔로우 해제
    @DeleteMapping("/auth/follow")
    public ResponseEntity unfollow(HttpServletRequest request, @RequestBody Map<String, Long> followRequest) {
        Map<String, Boolean> data = new HashMap<>();
        try {
            Long from = Long.valueOf((String) request.getAttribute("uid"));
            Long to = followRequest.get("to");
            data.put("success", memberService.unfollowUser(from, to));
            return new ResponseEntity(data, HttpStatus.CREATED);
        } catch(JpaObjectRetrievalFailureException e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            data.put("success", false);
            return new ResponseEntity(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
