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
public class GoogleConfigUtils {
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

    // Google 로그인 URL 생성 로직
    public String googleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getGoogleClientId());
        params.put("redirect_uri", getGoogleRedirectUri());
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

//        for (Map.Entry <String, Object> param: params.entrySet()) {
//            System.out.println("[key] : "+ param.getKey() + ", [value] : " + param.getValue());
//        }

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

//        System.out.println("[paramStr] : " + paramStr);

        return getGoogleLoginUrl()
                + "/o/oauth2/v2/auth"
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

    // scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함
    public String getScopeUrl() {
//        return scopes.stream().collect(Collectors.joining(","))
//                .replaceAll(",", "%20");
        return scopes.replaceAll(",", "%20");
    }
}