package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(PostBookmarkId.class)
public class PostBookmark{

    @Id
    @ManyToOne
    @JoinColumn(name = "m_id")
    private Member memberId;

    @Id
    @ManyToOne
    @JoinColumn(name = "p_id")
    private Post postId;

    public PostBookmark(){}
    public PostBookmark(Member member, Post post) {
        this.memberId = member;
        this.postId = post;
    }


}
