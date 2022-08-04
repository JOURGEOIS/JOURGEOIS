package com.jourgeois.backend.api.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Builder
@Getter @Setter
public class CustomCocktailDTO {
    private Long uid;
    private String nickname;
    private String profileImg;
    private String postIngredients;
    private String postRecipe;
    private Long postId;
    private String postImg;
    private String postTitle;
    private DateTime postCreateTime;
    private DateTime postLastUpdateTime;
    private String postDescription;
    private Integer postCount;
}
