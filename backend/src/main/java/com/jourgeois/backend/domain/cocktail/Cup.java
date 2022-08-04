package com.jourgeois.backend.domain.cocktail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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