package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUidAndEmail(Long uid, String email);
    Member findByNickname(String nickname);
    List<Member> findByNicknameContainingIgnoreCaseOrderByNickname(String nickname, Pageable pageable);
    List<Member> findTop10ByNicknameContainingIgnoreCaseOrderByNickname(String nickname);
    Optional<Member> findByNicknameAndUidIsNot(String nickname, Long uid);
    Optional<Member> findByEmailAndName(String email, String name);

    //@Query("select count(*) from follow where from_user_id = :id")
//    Optional<Member> findFollow(String id);
}