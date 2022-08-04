package com.jourgeois.backend.domain.cocktail;

import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.time.BaseTime;
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

    public CocktailComment(Member m, Cocktail c, String comment){
        this.member = m;
        this.cocktail = c;
        this.comment = comment;
    }
}
