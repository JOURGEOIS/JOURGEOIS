package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.PostReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReviewRepository extends JpaRepository<PostReview, Long> {
}
