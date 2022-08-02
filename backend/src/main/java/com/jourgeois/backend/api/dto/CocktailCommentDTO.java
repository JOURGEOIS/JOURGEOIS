package com.jourgeois.backend.api.dto;

import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CocktailCommentDTO {
    private String id;
    private String review;
    private String cocktailId;
    private String memberId;

}
