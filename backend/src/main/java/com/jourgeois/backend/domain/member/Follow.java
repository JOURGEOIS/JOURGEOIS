package com.jourgeois.backend.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IdClass(FollowPK.class)
public class Follow {
    @Id
    @JoinColumn(name = "from_user_id")
    @ManyToOne(fetch =  FetchType.LAZY)
    private Member from;

    @Id
    @JoinColumn(name = "to_user_id")
    @ManyToOne(fetch =  FetchType.LAZY)
    private Member to;
}
