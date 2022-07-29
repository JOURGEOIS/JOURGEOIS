package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.PasswordChangeForm;
import com.jourgeois.backend.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    Member findByNickname(String nickname);
    List<Member> findByNicknameContainingOrderByNickname(String nickname, Pageable pageable);
    Optional<Member> findByNicknameAndEmailIsNot(String nickname, String email);
    Optional<Member> findByEmailAndName(String email, String name);
}