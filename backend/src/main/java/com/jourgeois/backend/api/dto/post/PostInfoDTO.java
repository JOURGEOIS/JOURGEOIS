package com.jourgeois.backend.api.dto.post;

import com.jourgeois.backend.api.dto.member.ProfileDTO;
import com.jourgeois.backend.api.dto.post.PostDTO;
import lombok.Builder;

@Builder
public class PostInfoDTO {
    private PostDTO customCocktail;
    private ProfileDTO profileDTO;
    private Long like;
    public PostInfoDTO(){}

    public PostInfoDTO(PostDTO customCocktail, ProfileDTO profileDTO, Long like) {
        this.customCocktail = customCocktail;
        this.profileDTO = profileDTO;
        this.like = like;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public PostDTO getCustomCocktail() {
        return customCocktail;
    }

    public void setCustomCocktail(PostDTO customCocktail) {
        this.customCocktail = customCocktail;
    }

    public ProfileDTO getMember() {
        return profileDTO;
    }

    public void setMember(ProfileDTO profileDTO) {
        this.profileDTO = profileDTO;
    }

    @Override
    public String toString() {
        return "PostInfoDTO{" +
                "customCocktail=" + customCocktail +
                ", member=" + profileDTO +
                '}';
    }
}
