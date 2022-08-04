package com.jourgeois.backend.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // equals, hashcode
@NoArgsConstructor
@AllArgsConstructor
public class FollowPK implements Serializable {
    private Long from;
    private Long to;
}
