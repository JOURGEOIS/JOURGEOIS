package com.jourgeois.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cup {
    @Id
    @GeneratedValue
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_name_kr")
    private String nameKR;

    @OneToMany(mappedBy = "cupId")
    private List<Cocktail> cocktails = new ArrayList<>();
}