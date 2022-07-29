package com.jourgeois.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Cocktail {
    @Id @GeneratedValue
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_alcohol")
    private double alcohol;
    @Column(name = "c_cup_id")
    private int cupId;
    @Column(name = "c_tag")
    private String tag;
    @Column(name = "c_base_liquor")
    private String baseLiquor;
    @Column(name = "c_category")
    private String category;
    @Column(name = "c_recipe")
    private String recipe;
    @OneToMany(mappedBy = "cocktail")
    private List<CocktailToMaterial> cocktailToMaterials = new ArrayList<>();

    public Cocktail(Long id, String name, double alcohol, int cupId, String tag, String baseLiquor, String category, String recipe) {
        this.id = id;
        this.name = name;
        this.alcohol = alcohol;
        this.cupId = cupId;
        this.tag = tag;
        this.baseLiquor = baseLiquor;
        this.category = category;
        this.recipe = recipe;
    }
}
