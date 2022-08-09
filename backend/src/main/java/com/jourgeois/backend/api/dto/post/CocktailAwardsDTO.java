package com.jourgeois.backend.api.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class CocktailAwardsDTO {
    Long postId;
    String description;
    String imgLink;
    String title;
    Integer like;
    String percentage;
}
