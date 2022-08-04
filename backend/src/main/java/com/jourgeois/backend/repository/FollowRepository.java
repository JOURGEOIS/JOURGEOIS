package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.member.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
