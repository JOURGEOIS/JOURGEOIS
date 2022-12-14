package com.jourgeois.backend.domain.post;

import com.jourgeois.backend.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "p_dtype")
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "p_id")
    private Long id;

    @Column(name="p_dtype", insertable = false, updatable = false)
    protected String d_type;

    @Column(name = "p_img")
    private String img;

    @Column(name = "p_description", length = 500)
    private String description;

    @Column(name = "p_create_time")
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "p_last_update_time")
    @LastModifiedDate
    private LocalDateTime lastUpdateTime;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "p_writer")
    private Member member;

    @OneToMany(mappedBy = "postId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostBookmark> postBookmarks;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostReview> reviews;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", member=" + member +
//                ", postBookmarks=" + postBookmarks +
                ", reviews=" + reviews +
                '}';
    }

    public Post(Long id) {
        this.id = id;
    }
}
