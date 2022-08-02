package com.jourgeois.backend.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CustomCocktailToCocktail implements Serializable {
    @Id
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "p_id")
    private CustomCocktail customCocktail;

    @Id
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;
}
