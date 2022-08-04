package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.member.FollowerVO;
import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.FollowPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    @Query(value = "select member.uid as uid, member.nickname as nickname, member.profile_img as profileImg, (uid in (select to_user_id from follow where from_user_id = :uid)) as isFollowed from member where uid in (select from_user_id from follow where to_user_id = :uid)", nativeQuery = true)
    List<FollowerVO> getFollwerAll(@Param(value = "uid") Long uid, Pageable pageable);

//    Optional<Follow> findByFromAndTo(FollowPK key);
}
