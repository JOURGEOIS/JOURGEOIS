package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.post.PostReviewResponseVO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.PostReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostReviewRepository extends JpaRepository<PostReview, Long> {
    Optional<PostReview> findByIdAndMember(Long aLong, Member member);

    @Query(value = "select pr_create_time as createTime, pr_last_update_time as updateTime, pr_review as review, uid, nickname, profile_img, (pr_create_time < pr_last_update_time) as isUpdated "
                    + "from (select * from post_review where pr_p_id = :p_id) as pr "
                    + "left join member on pr.pr_uid = member.uid "
                    + "order by createTime DESC "
                    , nativeQuery = true)
    List<PostReviewResponseVO> getAllPostReviewsDesc(@Param("p_id") Long p_id, Pageable pageable);

    @Query(value = "select pr_create_time as createTime, pr_last_update_time as updateTime, pr_review as review, uid, nickname, profile_img, (pr_create_time < pr_last_update_time) as isUpdated "
            + "from (select * from post_review where pr_p_id = :p_id) as pr "
            + "left join member on pr.pr_uid = member.uid "
            + "order by createTime ASC "
            , nativeQuery = true)
    List<PostReviewResponseVO> getAllPostReviewsAsc(@Param("p_id") Long p_id, Pageable pageable);
}
