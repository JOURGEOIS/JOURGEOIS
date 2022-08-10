package com.jourgeois.backend.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.repository.MemberRepository;
import com.jourgeois.backend.security.MyUserDetailsService;
import com.jourgeois.backend.socialLogin.GoogleLoginDTO;
import com.jourgeois.backend.socialLogin.SocialLoginConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MemberSocialLoginService {
    private final MemberRepository memberRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final SocialLoginConfigUtils socialLoginConfigUtils;

    @Autowired
    public MemberSocialLoginService(MemberRepository memberRepository, MyUserDetailsService myUserDetailsService, SocialLoginConfigUtils socialLoginConfigUtils) {
        this.memberRepository = memberRepository;
        this.myUserDetailsService = myUserDetailsService;
        this.socialLoginConfigUtils = socialLoginConfigUtils;
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
        if(member.getPassword().equals(googleLoginDTO.getSub())){
            return userDetails;
        }
        else {
            throw new BadCredentialsException("uid : " + userDetails.getUsername() + " / Invalid social login");
        }
    }

    public Member signUpGoogleUser(GoogleLoginDTO gDTO){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
        Member m = Member.builder().email(gDTO.getEmail()).name(gDTO.getName()).SSOId(gDTO.getSub())
                .nickname("유저"+gDTO.getSub().substring(3,10) + date).profileImg("profile/default/1.png").build();
        try{
            memberRepository.save(m);
            memberRepository.flush();

            return memberRepository.findByEmail(gDTO.getEmail()).orElseThrow(()-> new Exception("signUpSocialUser"));
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public UserDetails loginUser(Map<String, Object> kakaoUserInfo, String domain){
        String email = (String) kakaoUserInfo.get("email");
        String id = (String) kakaoUserInfo.get("id");

        System.out.println(domain + " email : " + email);
        Member member = memberRepository.findByEmail(email).orElse(null) ;

        System.out.println(" ㅇ아이니ㅣㅣ여기 인가 ?");
        // 찾는 멤버가 없으면 리턴
        if(member == null){
            member = signUpSocialUser(email, id);
            System.out.println("멤버는 null 값 임니다 ");
        }


        UserDetails userDetails = myUserDetailsService.loadUserByUsername(member.getUid().toString());

        System.out.println("Kakao SSOId : " + "1");
        System.out.println("member.getSSOId : "+member.getSSOId());
        // DB에 있는 sub(password)와 들어온 sub와 일치하는 지 확인
        if(member.getPassword().equals(id.toString())){
            System.out.println("여ㅣ서 리턴");
            return userDetails;
        }
        else {
            throw new BadCredentialsException("uid : " + userDetails.getUsername() + " / Invalid social login");
        }
    }

    public Member signUpSocialUser(String email, String id){
        String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());

        Member m = Member.builder().email(email).SSOId(id)
                .profileImg("profile/default/1.png").nickname("유저" + date + Math.random() * 10000).build();
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
                Long id = element.getAsJsonObject().get("id").getAsLong();
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
}
