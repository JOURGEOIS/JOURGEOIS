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
@DiscriminatorValue("contest")
public class Contest extends  Post{
    @Column(name="contest_title")
    private String title;
}
