package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post_review")
public class PostReview {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pr_review", length = 200)
    private String review;

    @Column(name = "pr_create_time")
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "pr_last_update_time")
    @LastModifiedDate
    private LocalDateTime lastUpdateTime;

    @ManyToOne
    @JoinColumn(name = "pr_uid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "pr_p_id")
    private Post post;
}
