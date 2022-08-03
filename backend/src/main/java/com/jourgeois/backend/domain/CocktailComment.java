package com.jourgeois.backend.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CocktailComment extends BaseTime implements Serializable {
    @Id @GeneratedValue
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "c_id")
    private Cocktail cocktail;

    @Column(name = "cr_comment")
    private String comment;

    @Column(name = "c_likes")
    private Integer likes = 0;

    public CocktailComment(Member m, Cocktail c, String comment){
        this.member = m;
        this.cocktail = c;
        this.comment = comment;
    }
}
