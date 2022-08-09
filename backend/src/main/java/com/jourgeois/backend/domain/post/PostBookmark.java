package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@IdClass(PostBookmarkId.class)
@EntityListeners(AuditingEntityListener.class)
public class PostBookmark{

    @Id
    @ManyToOne
    @JoinColumn(name = "m_id")
    private Member memberId;

    @Id
    @ManyToOne
    @JoinColumn(name = "p_id")
    private Post postId;

    @Column(name = "b_timestamp")
    @CreatedDate
    private LocalDateTime timestamp;

    public PostBookmark(){}
    public PostBookmark(Member member, Post post) {
        this.memberId = member;
        this.postId = post;
    }


}
