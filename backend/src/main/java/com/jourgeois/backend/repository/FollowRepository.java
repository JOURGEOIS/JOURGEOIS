package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.member.FollowerVO;
import com.jourgeois.backend.domain.member.Follow;
import com.jourgeois.backend.domain.member.FollowPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    @Query(value = "select member.uid as uid, member.nickname as nickname, member.profile_img as profileImg, " +
            "case When (uid  = :me) then -1 Else (uid in (select to_user_id from follow where from_user_id = :me)) END as isFollowed " +
            "from member " +
            "where uid in (select from_user_id from follow where to_user_id = :uid)", nativeQuery = true)
    List<FollowerVO> getFollwerAll(@Param(value = "uid") Long uid, @Param(value = "me") Long me, Pageable pageable);

    @Query(value = "select member.uid as uid, member.nickname as nickname, member.profile_img as profileImg, " +
            "case when (uid = :me) then -1 Else 1 End as isFollowed from (select * from follow where from_user_id = :uid) as followee " +
            "left join member " +
            "on followee.to_user_id = member.uid", nativeQuery = true)
    List<FollowerVO> getFollweeAll(@Param(value = "uid") Long uid, @Param(value = "me") Long me, Pageable pageable);

}
