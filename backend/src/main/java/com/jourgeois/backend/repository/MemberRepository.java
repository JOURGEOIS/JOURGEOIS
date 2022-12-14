package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.member.MemberVO;
import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
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


    @Query("SELECT m.uid AS uid, m.nickname AS nickname, m.profileImg AS profileImg, m.isPrivate AS isPrivate, m.introduce AS introduce, m.email AS email, count(m) AS postCnt, " +
            "(SELECT cc.title FROM CustomCocktail AS cc WHERE p.id = cc.id) AS title, " +
            "(select COUNT(ff) FROM Follow AS ff WHERE ff.from.uid = :id) AS followingCnt, " +
            "(SELECT COUNT(ft) FROM Follow AS ft WHERE ft.to.uid = :id) AS followerCnt, " +
            "(SELECT COUNT(f) FROM Follow AS f WHERE f.from.uid = :myUid AND f.to.uid = :id) AS isFollowed " +
            "FROM Member AS m JOIN Post AS p ON m.uid = p.member.uid AND p.d_type <> 'cocktail_awards' WHERE m.uid = :id")
    Optional<MemberVO> findMemberProfile(Long myUid, Long id);

    @Query("SELECT m.uid AS uid, m.nickname AS nickname, m.profileImg AS profileImg " +
            "FROM Member AS m WHERE m.uid = :id")
    Optional<MemberVO> findMemberForChat(Long id);
}