package com.jourgeois.backend.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomCocktailId implements Serializable {
    private Long customCocktailId;
    private Long cocktailId;
}
