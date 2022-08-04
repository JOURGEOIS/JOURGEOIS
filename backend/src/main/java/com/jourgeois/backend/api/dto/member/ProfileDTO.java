package com.jourgeois.backend.api.dto.member;


import com.jourgeois.backend.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

//@Builder
@Getter @Setter
public class ProfileDTO {
    private Long uid;
    private String email;
    private String name;
    private String nickname;
    private String profileImg;
    private MultipartFile profileLink;
    private String introduce;

    public ProfileDTO(Long uid, String email, String name, String nickname, String profileImg, String introduce) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }


}
