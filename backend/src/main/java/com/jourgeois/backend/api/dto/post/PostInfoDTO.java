package com.jourgeois.backend.api.dto.post;

import com.jourgeois.backend.api.dto.member.ProfileDTO;
import com.jourgeois.backend.api.dto.post.PostDTO;
import lombok.Builder;

@Builder
public class PostInfoDTO {
    private PostDTO customCocktail;
    private ProfileDTO profileDTO;

    public PostInfoDTO(){}

    public PostInfoDTO(PostDTO post, ProfileDTO profileDTO) {
        this.customCocktail = post;
        this.profileDTO = profileDTO;
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
