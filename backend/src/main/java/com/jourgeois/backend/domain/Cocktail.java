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
    @Id
    @Column(name = "c_id", unique = true)
    private Long id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_name_kr")
    private String nameKR;
    @Column(name = "c_alcohol")
    private Double alcohol;
    @ManyToOne
    private Cup cupId;
    @Column(name = "c_tag")
    private String tag;
    @Column(name = "c_base_liquor")
    private String baseLiquor;
    @Column(name = "c_category")
    private String category;
    @Column(name = "c_recipe", length = 500)
    private String recipe;
    @Column(name = "c_type")
    private String type;
    @Column(name = "c_img")
    private String img;

    @OneToMany(mappedBy = "cocktail")
    private List<CocktailToMaterial> cocktailToMaterials = new ArrayList<>();

    @OneToMany(mappedBy = "cocktail")
    private List<CocktailBookmark> cocktailBookmarks;

}
