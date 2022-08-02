package com.jourgeois.backend.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class PostBookmark implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "m_id")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "p_id")
    private Post post;

}
