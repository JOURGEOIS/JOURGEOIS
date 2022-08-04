package com.jourgeois.backend.domain.cocktail;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CocktailToMaterial implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    @Id
    @ManyToOne
    @JoinColumn(name = "m_id")
    private Material material;
}
