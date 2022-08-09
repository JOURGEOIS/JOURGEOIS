package com.jourgeois.backend.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@NoArgsConstructor
@DiscriminatorValue("cocktail_awards")
public class CocktailAwards extends  Post{
    @Column(name="contest_title")
    private String title;
}
