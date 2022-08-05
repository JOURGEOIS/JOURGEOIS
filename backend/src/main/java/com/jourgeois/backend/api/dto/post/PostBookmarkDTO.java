package com.jourgeois.backend.api.dto.post;

import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class PostBookmarkDTO {
    private Member member;
    private Post post;
}
