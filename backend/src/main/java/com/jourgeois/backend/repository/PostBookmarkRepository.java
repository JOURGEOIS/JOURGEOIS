package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.domain.post.PostBookmark;
import com.jourgeois.backend.domain.post.PostBookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, PostBookmarkId> {
    Long countByPostId(Post p_id);
}
