package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.member.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PostBookmark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "m_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "p_id")
    private Post post;

}
