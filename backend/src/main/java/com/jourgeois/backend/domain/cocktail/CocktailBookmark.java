package com.jourgeois.backend.domain.cocktail;

import com.jourgeois.backend.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@IdClass(CocktailBookmarkId.class)
public class CocktailBookmark{

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(nullable = false)
//    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name="m_id")
    private Member memberId;

    @Id
    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktailId;

    @Builder
    public CocktailBookmark(Member member, Cocktail cocktail){
        this.memberId = member;
        this.cocktailId = cocktail;
    }

}
