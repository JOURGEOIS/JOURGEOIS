package com.jourgeois.backend.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class CocktailBookmark{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="m_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    @Builder
    public CocktailBookmark(Member member, Cocktail cocktail){
        this.member = member;
        this.cocktail = cocktail;
    }

}
