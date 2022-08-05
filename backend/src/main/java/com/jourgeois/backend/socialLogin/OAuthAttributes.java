package com.jourgeois.backend.socialLogin;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        //(new!) naver
//        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
//        }
        // google
//        return ofGoogle(userNameAttributeName, attributes);
    }
    // (new!)
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        // JSON형태이기 떄문에 Map을 통해서 데이터를 가져온다.
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // ofGoogle 로직 생략...

//    public User toEntity(){
//        return User.builder()
//                .name(name)
//                .email(email)
//                .picture(picture)
//                .role(Role.GUEST) // 기본 권한 GUEST
//                .build();
//    }
}
