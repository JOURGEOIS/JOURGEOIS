package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.domain.post.PostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    Optional<PostBookmark> findByMemberAndPost(Member member, Post post);
    Long countByPost(Post post);
}
