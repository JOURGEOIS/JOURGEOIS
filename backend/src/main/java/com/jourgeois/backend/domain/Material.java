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
public class Material {
    @Id @GeneratedValue
    @Column(name = "m_id")
    private Long id;

    @Column(name = "m_abv")
    private int abv;
    @Column(name = "m_name")
    private String name;

    @Column(name = "m_name_kr")
    private String nameKr;
    @Column(name = "m_img")
    private String img;

    @Column(name = "m_category")
    private String category;

    @Column(name = "m_type")
    private String type;

    @OneToMany(mappedBy = "material")
    private List<CocktailToMaterial> cocktailToMaterials = new ArrayList<>();

    public Material(Long id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
}
