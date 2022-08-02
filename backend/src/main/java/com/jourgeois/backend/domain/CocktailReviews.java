package com.jourgeois.backend.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CocktailReviews extends BaseTime implements Serializable {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    @Column(name = "cr_review")
    private String review;


    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

}
