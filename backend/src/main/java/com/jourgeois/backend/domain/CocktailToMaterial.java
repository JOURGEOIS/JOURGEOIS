package com.jourgeois.backend.domain;

import javax.persistence.*;

@Entity
public class CocktailToMaterial {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name = "m_id")
    private Material material;

}
