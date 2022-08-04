package com.jourgeois.backend.domain.cocktail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailBookmarkId implements Serializable {
    private Long memberId;
    private Long cocktailId;
}
