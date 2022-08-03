package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.domain.PostReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostReviewRepository extends JpaRepository<PostReview, Long> {

    Optional<PostReview> findByIdAndMember(Long aLong, Member member);
}
