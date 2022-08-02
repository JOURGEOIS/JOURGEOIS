package com.jourgeois.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
