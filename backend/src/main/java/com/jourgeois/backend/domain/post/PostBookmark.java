package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class PostBookmark{

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

    public PostBookmark(){}
    public PostBookmark(Member member, Post post) {
        this.member = member;
        this.post = post;
    }
}
