package com.jourgeois.backend.api.dto;

import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class CocktailBookmarkDTO {
    private Member member;
    private Cocktail cocktail;
}
