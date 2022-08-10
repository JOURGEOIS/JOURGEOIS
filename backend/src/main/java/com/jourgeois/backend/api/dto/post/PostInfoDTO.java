package com.jourgeois.backend.api.dto.post;

import com.jourgeois.backend.api.dto.member.FollowerDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class PostInfoDTO {
    private PostDTO customCocktail;
    private FollowerDTO followerDTO;
}
