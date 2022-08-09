package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.post.NewsFeedVO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndMember(Long id, Member member);

    @Query(value = "select p_create_time as createTime, p_last_update_time as updateTime, (p_create_time < p_last_update_time) as isUpdated, p_id as pid, p_dtype as type, p_writer as writer, nickname, profile_img as profileImg, \n" +
            "isSuperCustomCocktail, c_id as baseCocktailId, c_name_kr as baseCocktailName, cc_cocktail_title as cocktailTitle, p_img as postImg, p_description as description,\n" +
            // "(select count(*) from follow where to_user_id = uid) as followerCount, \n" +
            "(select count(*) from post_review where pr_p_id = p_id) as reviewCount, \n" +
            "(select count(*) from post_bookmark where post_bookmark.p_id = pid) as likeCount,\n" +
            "(select count(*) from post_bookmark where post_bookmark.p_id = pid and post_bookmark.m_id = :me) as isLiked\n" +
            "from member join\n" +
            "(select * from (select * from post where p_writer in (select to_user_id from follow where from_user_id = :me) or p_writer = :me) as followerFeed left join \n" +
            "(select * from (select cc_cocktail_ingredients, cc_cocktail_recipe, cc_cocktail_title, custom_cocktail.p_id as cock_p_id, c_id as base_c_id, c_id is null as isSuperCustomCocktail from custom_cocktail \n" +
            "left join custom_cocktail_to_cocktail on custom_cocktail.p_id = custom_cocktail_to_cocktail.p_id) as cocktailFilter left join cocktail on cocktailFilter.base_c_id = cocktail.c_id) as cocktailInfo\n" +
            "on followerFeed.p_id = cocktailInfo.cock_p_id) as postInfo on member.uid = postInfo.p_writer order by createTime desc", nativeQuery = true)
    List<NewsFeedVO> getNewsFeed(@Param("me") Long me, Pageable pageable);
}
