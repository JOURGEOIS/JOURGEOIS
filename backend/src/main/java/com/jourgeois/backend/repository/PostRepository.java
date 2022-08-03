package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Member;
import com.jourgeois.backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndMember(Long id, Member member);
}
