package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.cocktail.Cocktail;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(CustomCocktailId.class)
public class CustomCocktailToCocktail{

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_id")
    private CustomCocktail customCocktailId;

    @Id
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktailId;

    public CustomCocktailToCocktail(CustomCocktail customCocktail, Cocktail cocktail) {
        this.customCocktailId = customCocktail;
        this.cocktailId = cocktail;
    }

    public CustomCocktailToCocktail() {}

    public CustomCocktail getCustomCocktailId() {
        return customCocktailId;
    }

    public void setCustomCocktailId(CustomCocktail customCocktailId) {
        this.customCocktailId = customCocktailId;
    }

    public Cocktail getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(Cocktail cocktailId) {
        this.cocktailId = cocktailId;
    }
}
