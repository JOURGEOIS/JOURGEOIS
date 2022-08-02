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
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    @Column(name = "cr_review")
    private String review;

    public CocktailReviews(Member m, Cocktail c, String review){
        this.member = m;
        this.cocktail = c;
        this.review = review;
    }
}
