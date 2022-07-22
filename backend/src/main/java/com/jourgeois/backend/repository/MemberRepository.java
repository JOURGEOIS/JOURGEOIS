package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.PasswordChangeForm;
import com.jourgeois.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByNicknameAndEmailIsNot(String nickname, String email);
    Optional<Member> findByEmailAndPassword(String email, String password);
}