package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.domain.post.PostBookmark;
import com.jourgeois.backend.domain.post.PostBookmarkId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, PostBookmarkId> {
    Integer countByPostId(Post p_id);
    List<PostBookmark> findByPostId(Post p_id, Pageable pageable);
}
