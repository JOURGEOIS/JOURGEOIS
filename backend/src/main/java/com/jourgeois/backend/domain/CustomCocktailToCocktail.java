package com.jourgeois.backend.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CustomCocktailToCocktail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_id")
    private CustomCocktail customCocktail;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    public CustomCocktailToCocktail(CustomCocktail customCocktail, Cocktail cocktail) {
        this.customCocktail = customCocktail;
        this.cocktail = cocktail;
    }

    public CustomCocktailToCocktail() {}

    public Long getId() {
        return id;
    }
}
