package com.jourgeois.backend.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jourgeois.backend.api.dto.member.FollowerDTO;
import com.jourgeois.backend.api.dto.member.FollowerVO;
import com.jourgeois.backend.api.dto.member.MemberDTO;
import com.jourgeois.backend.api.dto.member.PasswordChangeForm;
import com.jourgeois.backend.api.dto.auth.TokenResponseDTO;
import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.auth.RefreshToken;
import com.jourgeois.backend.repository.FollowRepository;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.repository.auth.RefreshTokenRepository;
import com.jourgeois.backend.security.MyUserDetailsService;
import com.jourgeois.backend.security.jwt.JwtTokenProvider;
import com.jourgeois.backend.socialLogin.GoogleLoginDTO;
import com.jourgeois.backend.socialLogin.SocialLoginConfigUtils;
import com.jourgeois.backend.util.ImgType;
import com.jourgeois.backend.util.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final S3Util s3Util;
    private final String s3Url;
    private final SocialLoginConfigUtils socialLoginConfigUtils;


    @Autowired
    MemberService(MemberRepository memberRepository, FollowRepository followRepository, PasswordEncoder passwordEncoder,
                  JwtTokenProvider jwtTokenProvider,
                  MyUserDetailsService myUserDetailsService,
                  RefreshTokenRepository refreshTokenRepository,
                  S3Util s3Util,
                  @Value("${cloud.aws.s3.bucket.path}") String s3Url, SocialLoginConfigUtils socialLoginConfigUtils){
        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.myUserDetailsService = myUserDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.s3Util = s3Util;
        this.s3Url = s3Url;
        this.socialLoginConfigUtils = socialLoginConfigUtils;
    }

    public boolean signUp(Member m) throws Exception {
        try {
            validateDuplicateUser(m.getEmail());
            m.setPassword(passwordEncoder.encode(m.getPassword()));
            memberRepository.save(m);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean changeProfile(MemberDTO data){
        memberRepository.findByNicknameAndUidIsNot(data.getNickname(), data.getUid())
                .ifPresentOrElse(
                        (member -> {throw new IllegalArgumentException("닉네임 중복");}),
                        ()->memberRepository.findById(data.getUid())
                                .ifPresent(member -> {
                                    member.setName(data.getName());
                                    member.setIntroduce(data.getIntroduce());
                                    member.setNickname(data.getNickname());
                                    try{
                                        if(data.getProfileLink()!=null && !data.getProfileLink().isEmpty()){
                                            if(!member.getProfileImg().equals("profile/default/1.png")) {
                                                s3Util.deleteFile(member.getProfileImg());
                                            }
                                            member.setProfileImg(s3Util.upload(data.getProfileLink(), member.getUid(), ImgType.PROFILE));
                                        }
                                    } catch (IOException e){
                                        throw new IllegalArgumentException("이미지 업로드 오류");
                                    } finally {
                                        System.out.println("before finally");
                                        memberRepository.save(member);
                                        System.out.println("after finally");
                                    }
                                })
                );
        return true;
    }

    public boolean checkEmail(String email){
        Optional<Member> result = memberRepository.findByEmail(email);
        // 중복된 이메일이 없다면 사용 가능 return
        if(result.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkNickname(String nickname){
        Member result = memberRepository.findByNickname(nickname);
        // 중복된 닉네임이 없다면 사용 가능 return
        if(result==null) {
            return true;
        } else {
            return false;
        }
    }

    public UserDetails loginUser(String email, String password){
        Member member = memberRepository.findByEmail(email).get();
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(member.getUid().toString());

        // pw 확인
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }
        return userDetails;
    }

    public UserDetails loginUser(GoogleLoginDTO googleLoginDTO){
        System.out.println("GOOGLE EMAIL : " + googleLoginDTO.getEmail());
        Member member = memberRepository.findByEmail(googleLoginDTO.getEmail()).orElse(null) ;
        UserDetails userDetails = null;

        if(member == null){ // 찾는 멤버가 없으면 리턴
            member = signUpGoogleUser(googleLoginDTO);
        }

        userDetails = myUserDetailsService.loadUserByUsername(member.getUid().toString());

        System.out.println("member.getPassword() : "+member.getPassword());
        System.out.println("googleLoginDTO.getSub() : "+googleLoginDTO.getSub());

        // DB에 있는 sub(password)와 들어온 sub와 일치하는 지 확인
        if(member.getSSOId().equals(googleLoginDTO.getSub())){
            return userDetails;
        }
        else {
            throw new BadCredentialsException("uid : " + userDetails.getUsername() + " / Invalid social login");
        }
    }

    public Member signUpGoogleUser(GoogleLoginDTO gDTO){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        Member m = Member.builder().email(gDTO.getEmail()).name(gDTO.getName()).SSOId(gDTO.getSub()).password("1q2w3e4r")
                .nickname("유저" + date + Math.round((Math.random() * 100000))).profileImg("profile/default/1.png").build();
        try{
            memberRepository.save(m);
            memberRepository.flush();

            return memberRepository.findByEmail(gDTO.getEmail()).orElseThrow(()-> new Exception("signUpSocialUser"));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public UserDetails loginSocialUser(Map<String, Object> socialUserInfo, String domain){
        String email = (String) socialUserInfo.get("email");
        String id = (String) socialUserInfo.get("id");

        System.out.println("=============================");
        System.out.println("[loginSocialUser] 도메인명 : " + domain + " 이메일 : " + email + " 아이디 : " + id);
        System.out.println("=============================");

        Member member = memberRepository.findByEmail(email).orElse(null) ;

        // 찾는 멤버가 없으면 리턴
        if(member == null){
            member = signUpSocialUser(email, id);
        }

        System.out.println("member uid : " + member.getUid());

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(member.getUid().toString());
        System.out.println(userDetails.getUsername());
        System.out.println(domain+" SSOId : " + id);
        System.out.println("member.getSSOId : " + member.getSSOId());
        // DB에 있는 sso id와 들어온 sub와 일치하는 지 확인
        if(member.getSSOId().equals(id)){
            System.out.println("여ㅣ서 리턴");
            return userDetails;
        }
        else {
            throw new BadCredentialsException("uid : " + userDetails.getUsername() + " / Invalid social login");
        }
    }

    public Member signUpSocialUser(String email, String id){
        System.out.println("=============================");
        System.out.println("[SignUpSocialUser] : "+ email.split("/")[0] + "로 가입된 " + id + " 사용자가 없어 회원가입 절차로 넘어갑니다.");
        System.out.println("=============================");

        String date = DateTimeFormatter.ofPattern("yyMMdd").format(LocalDate.now());

        // 유저 이름은 난수 생성. yyMMdd+ 0 < n < 100000 사이의 값을 더해 만들어짐
        Member m = Member.builder().email(email).SSOId(id).password("1q2w3e4r")
                .profileImg("profile/default/1.png").nickname("유저" + date + Math.round((Math.random() * 100000))).build();
        try{
            memberRepository.save(m);
            memberRepository.flush();

            return memberRepository.findByEmail(m.getEmail()).orElseThrow(()-> new Exception("signUpSocialUser"));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getKakaoAccessToken(String code, String domain) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "";


        if(domain == "kakao") reqURL = "https://kauth.kakao.com/oauth/token";
        else if(domain == "naver") reqURL = "https://nid.naver.com/oauth2.0/token";

        System.out.println("=======================");
        System.out.println("Now Domain : " + domain);
        System.out.println("Now Code : " + code);

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            if(domain == "kakao"){
                sb.append("grant_type=authorization_code");
                sb.append("&client_id=" + socialLoginConfigUtils.getKakaoRestapiKey());
                sb.append("&redirect_uri="+ socialLoginConfigUtils.getKakaoRedirectUrl());
                sb.append("&client_secret=" + socialLoginConfigUtils.getKakaoClientSecret());
                sb.append("&code=" + code);
            } else if (domain == "naver"){
                sb.append("grant_type=authorization_code");
                sb.append("&client_id=" + socialLoginConfigUtils.getNaverClientId());
                sb.append("&client_secret=" + socialLoginConfigUtils.getNaverClientSecret());
                sb.append("&state=" + "9kgsGTfH4j7IyAkg");
                sb.append("&code=" + code);
            }

            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            System.out.println("=======================");

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return access_Token;

    }

    public Map<String, Object> getSocialUserInfo(String accessToken, String domain){
        String reqURL = "";
        if(domain == "kakao") reqURL = "https://kapi.kakao.com/v2/user/me";
        if(domain == "naver") reqURL = "https://openapi.naver.com/v1/nid/me";

        System.out.println("[getSocialUserInfo] Now Domain : " + domain);
        Map<String, Object> res = new HashMap<>();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            if(domain == "kakao") {
                String id = element.getAsJsonObject().get("id").getAsString();
                boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
                String email = "";
                if (hasEmail) {
                    email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
                }

                // kakao 로그인은 앞에 kakao/ 를 붙임
                email = "kakao/" + email;

                System.out.println("getKakaoUserInfo id : " + id);
                System.out.println("getKakaoUserInfo email : " + email);

                res.put("id", id);
                res.put("email", email);
            }
            else if(domain == "naver"){
                String resultCode = element.getAsJsonObject().get("resultcode").getAsString();
                String message = element.getAsJsonObject().get("message").getAsString();

                System.out.println("naver 도메인 들어옴");
                System.out.println("resultCode : "+ resultCode);
                res.put("message", message);

                if(resultCode == "00" || message == "success" || true){
                    String id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
                    String profileImage = element.getAsJsonObject().get("response").getAsJsonObject().get("profile_image").getAsString();
                    String email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
                    String name = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();

//                    오류발생 이유 무엇 ?
//                    String resp = element.getAsJsonObject().get("response").getAsString();
//                    System.out.println("response : " + resp);

                    email = "naver/" + email;

                    System.out.println("id" + id);
                    System.out.println("profileImage" + profileImage);
                    System.out.println("name" + name);
                    System.out.println("email" + email);

                    res.put("id", id);
                    res.put("profileImage", profileImage);
                    res.put("email", email);
                    res.put("name", name);
                }
            }

            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }


    @Transactional
    public TokenResponseDTO createToken(UserDetails userDetails) {
        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        // refresh token 발급 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);
        RefreshToken token = RefreshToken.createToken(new Member(Long.valueOf(userDetails.getUsername())), refreshToken);
        System.out.println(refreshToken + " " + token);

        // 기존 토큰이 있으면 수정, 없으면 생성
        refreshTokenRepository.findById(Long.valueOf(userDetails.getUsername()))
                .ifPresentOrElse(
                        (tokenEntity)->{tokenEntity.changeToken(refreshToken);},
                        ()->{refreshTokenRepository.save(RefreshToken.createToken(memberRepository.findById(Long.valueOf(userDetails.getUsername())).get(), refreshToken));
                            }
                );

        return TokenResponseDTO.builder()
                .accessToken("Bearer-"+jwtTokenProvider.createAccessToken(authentication))
                .refreshToken("Bearer-"+refreshToken)
                .build();
    }

    @Transactional
    public MemberDTO findUserInfo(Long uid){
        Member member = memberRepository.findById(uid).get();
        MemberDTO p = new MemberDTO(member.getUid(), member.getEmail(), member.getName(),
                member.getNickname(), s3Url + member.getProfileImg(), member.getIntroduce());
        return p;

    }

    @Transactional
    public void logout(Long uid){
        refreshTokenRepository.deleteById(uid);
        System.out.println("토큰 삭제 완료");
    }

    private void validateDuplicateUser(String email){
        System.out.println(email + " email");
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
//                    log.debug("email : {}, 이메일 중복으로 회원가입 실패", email);
                    throw new RuntimeException("이메일 중복");
                });
    }

    @Transactional
    public void signOut(Long uid, String email) throws NoSuchElementException{
        Optional<Member> member = memberRepository.findByUidAndEmail(uid, email);
        member.ifPresentOrElse(selectMember -> {
            refreshTokenRepository.deleteById(selectMember.getUid());
            String userProfile = selectMember.getProfileImg();
            if(!userProfile.equals("default/1.png"))
                s3Util.deleteFile(userProfile);
            memberRepository.delete(selectMember);
        }, () -> {
            throw new NoSuchElementException("가입된 회원이 아닙니다.");
        });
        System.out.println("회원 탈퇴 완료");
    }

    @Transactional
    public void changePassword(PasswordChangeForm passwordChangeForm) throws IllegalArgumentException, NoSuchElementException {
        Long uid = passwordChangeForm.getUid();
        memberRepository.findById(uid)
                .ifPresentOrElse((member)-> {
                            if(passwordChangeForm.getPasswordNew().equals(passwordChangeForm.getPasswordConfirm())) {
                                member.setPassword(passwordEncoder.encode(passwordChangeForm.getPasswordNew()));
                                memberRepository.save(member);
                            } else {
                                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                            }
                        },
                        () -> {throw new NoSuchElementException("존재하지 않는 회원 입니다.");});
    }

    @Transactional
    public boolean checkPassword(PasswordChangeForm passwordChangeForm) throws NoSuchElementException {
        Long uid = passwordChangeForm.getUid();
        Member member = memberRepository.findById(uid).get();
        return passwordEncoder.matches(passwordChangeForm.getPasswordOld(), member.getPassword());
    }

    @Transactional
    public void findPassword(PasswordChangeForm passwordChangeForm) throws IllegalArgumentException, NoSuchElementException {
        String email = passwordChangeForm.getEmail();
        memberRepository.findByEmail(email)
                .ifPresentOrElse((member)-> {
                            if(passwordChangeForm.getPasswordNew().equals(passwordChangeForm.getPasswordConfirm())) {
                                member.setPassword(passwordEncoder.encode(passwordChangeForm.getPasswordNew()));
                                memberRepository.save(member);
                            } else {
                                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
                            }
                        },
                        () -> {throw new NoSuchElementException("가입 정보가 없습니다.");}
                );
    }

    public boolean checkUser(String email, String userName) {
        return memberRepository.findByEmailAndName(email, userName).isPresent();
    }

    public boolean checkUserUid(Long uid){
        return memberRepository.findById(uid).isPresent();
    }

    @Transactional
    public String ProfileImageLocalUpload(MemberDTO memberDto) throws IOException {
        Member member = memberRepository.findById(memberDto.getUid()).get();
        String url = s3Util.localUpload(memberDto.getProfileLink(), member.getUid(), ImgType.PROFILE);
        return url;
    }

    public boolean followUser(Long from, Long to) throws IllegalArgumentException, Exception{
        Follow follow = new Follow();

        Member follower = new Member();
        follower.setUid(from);
        Member followee = new Member();
        followee.setUid(to);

        follow.setFrom(follower);
        follow.setTo(followee);
        followRepository.save(follow);

        return true;
    }

    public List<FollowerDTO> getFollowerAll(Long uid, Long me, Pageable pageable) throws NumberFormatException{
        List<FollowerVO> followers = followRepository.getFollwerAll(uid, me, pageable);

        List<FollowerDTO> followersResponse = new ArrayList<>();

        followers.forEach((follower) -> {
            FollowerDTO followerDTO = FollowerDTO.builder()
                    .isFollowed(follower.getIsFollowed())
                    .nickname(follower.getNickname())
                    .uid(follower.getUid())
                    .profileImg(s3Url + follower.getProfileImg())
                    .introduce(follower.getIntroduce())
                    .build();
            followersResponse.add(followerDTO);
        });
        return followersResponse;
    }

    public Object getFolloweeAll(Long uid, Long me, Pageable pageable) {
        List<FollowerVO> followers = followRepository.getFollweeAll(uid, me, pageable);

        List<FollowerDTO> followeesResponse = new ArrayList<>();

        followers.forEach((follower) -> {
            FollowerDTO followerDTO = FollowerDTO.builder()
                    .isFollowed(follower.getIsFollowed())
                    .nickname(follower.getNickname())
                    .uid(follower.getUid())
                    .profileImg(s3Url + follower.getProfileImg())
                    .introduce(follower.getIntroduce())
                    .build();
            followeesResponse.add(followerDTO);
        });
        return followeesResponse;
    }

    public boolean unfollowUser(Long from, Long to) {
        Follow follow = new Follow();

        Member follower = new Member();
        follower.setUid(from);
        Member followee = new Member();
        followee.setUid(to);

        follow.setFrom(follower);
        follow.setTo(followee);
        followRepository.delete(follow);

        return true;
    }
}
