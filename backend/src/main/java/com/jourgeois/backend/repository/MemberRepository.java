package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.PasswordChangeForm;
import com.jourgeois.backend.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
//    Optional<Member> findByUid(Long uid);
    Member findByNickname(String nickname);
    List<Member> findByNicknameContainingIgnoreCaseOrderByNickname(String nickname, Pageable pageable);
    List<Member> findTop10ByNicknameContainingIgnoreCaseOrderByNickname(String nickname);
    Optional<Member> findByNicknameAndEmailIsNot(String nickname, String email);
    Optional<Member> findByEmailAndName(String email, String name);
}