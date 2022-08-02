package com.jourgeois.backend.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("cocktail")
public class CustomCocktail extends Post {
    @Column(name = "cc_cocktail_title")
    private String title;

    @Column(name = "cc_cocktail_ingredients")
    private String ingredients;

    @Column(name = "cc_cocktail_recipe")
    private String recipe;
}
