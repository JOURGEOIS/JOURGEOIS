package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.cocktail.Cocktail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("cocktail")
public class CustomCocktail extends Post {
    @Column(name = "cc_cocktail_title")
    private String title;

    @Column(name = "cc_cocktail_ingredients")
    private String ingredients;

    @Column(name = "cc_cocktail_recipe")
    private String recipe;

    public CustomCocktail(Long p_id) {
        this.setId(p_id);
    }

    public CustomCocktail(CustomCocktail customCocktailId) {
        this.setId(customCocktailId.getId());
    }
}
