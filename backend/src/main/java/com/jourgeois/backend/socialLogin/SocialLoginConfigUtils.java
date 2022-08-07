package com.jourgeois.backend.socialLogin;

// @Value를 사용하기 위해 @Component 추가
// 해당 클래스를 Bean으로 생성해둠
// 추후 사용할 일이 있을 때, Bean 인스턴스를 꺼내 활용

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:social-login.properties")
public class SocialLoginConfigUtils {
    @Value("${google.auth.url}")
    private String googleAuthUrl;
    @Value("${google.login.url}")
    private String googleLoginUrl;
    @Value("${google.redirect.uri}")
    private String googleRedirectUrl;
    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.secret}")
    private String googleSecret;
    @Value("${google.auth.scope}")
    private String scopes;

    @Value("${kakao.auth.url}")
    private String kakaoAuthUrl;
    @Value("${kakao.restapi.key}")
    private String kakaoRestapiKey;
    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUrl;
    @Value("${kakao.auth.scope}")
    private String kakaoAuthScope;
    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    @Value("${naver.auth.url}")
    private String naverAuthUrl;
    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.secret}")
    private String naverClientSecret;
    @Value("${naver.redirect.uri}")
    private String naverRedirectUrl;



    // Google 로그인 URL 생성 로직
    public String googleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getGoogleClientId());
        params.put("redirect_uri", getGoogleRedirectUri());
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getGoogleLoginUrl()
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr;
    }

    public String kakaoInitUrl(){
        Map<String, Object> params = new HashMap<>();
        params.put("redirect_uri", getKakaoRedirectUrl());
        params.put("response_type", "code");
        params.put("scope", getKakaoAuthScope());

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getKakaoAuthUrl()
                + getKakaoRestapiKey()
                + "&"
                + paramStr;
    }

    public String naverInitUrl(){
        Map<String, Object> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", getNaverClientId());
        params.put("redirect_uri", getNaverRedirectUrl());
        params.put("state", "STATE_STRING");

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getNaverAuthUrl()
                + "?"
                + paramStr;
    }


    public String getGoogleAuthUrl() {
        return googleAuthUrl;
    }
    public String getGoogleLoginUrl() {
        return googleLoginUrl;
    }
    public String getGoogleClientId() {
        return googleClientId;
    }
    public String getGoogleRedirectUri() {
        return googleRedirectUrl;
    }
    public String getGoogleSecret() {
        return googleSecret;
    }
    public String getKakaoAuthUrl() {
        return kakaoAuthUrl;
    }
    public String getKakaoRestapiKey() {
        return kakaoRestapiKey;
    }
    public String getKakaoRedirectUrl() {
        return kakaoRedirectUrl;
    }

    public String getKakaoAuthScope(){
        return kakaoAuthScope;
    }

    public String getKakaoClientSecret(){
        return kakaoClientSecret;
    }

    public String getNaverClientId(){
        return naverClientId;
    }
    public String getNaverClientSecret(){
        return naverClientSecret;
    }
    public String getNaverRedirectUrl() {
        return naverRedirectUrl;
    }
    public String getNaverAuthUrl() {
        return naverAuthUrl;
    }

    // scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함
    public String getScopeUrl() {
//        return scopes.stream().collect(Collectors.joining(","))
//                .replaceAll(",", "%20");
        return scopes.replaceAll(",", "%20");
    }
}