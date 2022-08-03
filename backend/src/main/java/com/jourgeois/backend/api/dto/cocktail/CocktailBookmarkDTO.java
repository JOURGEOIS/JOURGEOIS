package com.jourgeois.backend.api.dto.cocktail;

import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class CocktailBookmarkDTO {
    private Member member;
    private Cocktail cocktail;
}
