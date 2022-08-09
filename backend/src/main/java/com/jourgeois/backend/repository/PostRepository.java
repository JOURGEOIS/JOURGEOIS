package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.home.HomeCocktailItemVO;
import com.jourgeois.backend.api.dto.member.MemberVO;
import com.jourgeois.backend.api.dto.post.NewsFeedVO;
import com.jourgeois.backend.api.dto.post.cocktail.CocktailVO;
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

    @Query(value = "select custom_cocktail.p_id AS cocktailId, p_img AS img, cc_cocktail_title AS title, c_name_kr AS base from (select * from post where p_dtype = 'cocktail') as post\n" +
            "left join custom_cocktail\n" +
            "on post.p_id = custom_cocktail.p_id\n" +
            "left join custom_cocktail_to_cocktail\n" +
            "on custom_cocktail.p_id = custom_cocktail_to_cocktail.p_id\n" +
            "left join cocktail\n" +
            "on custom_cocktail_to_cocktail.c_id = cocktail.c_id\n" +
            "order by p_create_time DESC", nativeQuery = true)
    List<HomeCocktailItemVO> findCustomCocktailOrderByCreateTime(Pageable pageable);

    @Query(value = "select custom_cocktail.p_id AS cocktailId, p_img AS img, cc_cocktail_title AS title, c_name_kr AS base from (select * from post where p_dtype = 'cocktail') as post\n" +
            "left join custom_cocktail\n" +
            "on post.p_id = custom_cocktail.p_id\n" +
            "left join custom_cocktail_to_cocktail\n" +
            "on custom_cocktail.p_id = custom_cocktail_to_cocktail.p_id\n" +
            "left join cocktail\n" +
            "on custom_cocktail_to_cocktail.c_id = cocktail.c_id\n" +
            "order by p_create_time DESC LIMIT 5", nativeQuery = true)
    List<HomeCocktailItemVO> findTop5CustomCocktailOrderByCreateTime();

    @Query(value = "select count(m_id) as bookmarked, cocktail.c_id as cocktailId, c_alcohol as abv, c_base_liquor as base, c_img as img, c_name as title from\n" +
            "cocktail\n" +
            "left join\n" +
            "cocktail_bookmark\n" +
            "on\n" +
            "cocktail.c_id = cocktail_bookmark.c_id\n" +
            "group by cocktail.c_id order by bookmarked DESC LIMIT 5", nativeQuery = true)
    List<HomeCocktailItemVO> findTop5CocktailOrderByBookmarked();

    @Query(value = "select count(m_id) as bookmarked, cocktail.c_id as cocktailId, c_alcohol as abv, c_base_liquor as base, c_img as img, c_name as title from\n" +
            "cocktail\n" +
            "left join\n" +
            "cocktail_bookmark\n" +
            "on\n" +
            "cocktail.c_id = cocktail_bookmark.c_id\n" +
            "group by cocktail.c_id order by bookmarked DESC", nativeQuery = true)
    List<HomeCocktailItemVO> findCocktailOrderByBookmarked(Pageable pageable);

    @Query("SELECT m.nickname AS nickname, m.profileImg AS profileImg, p.createTime AS createTime, p.img AS img, p.description AS description " +
            "FROM Member AS m JOIN Post p ON p.member.uid = m.uid AND p.d_type = :postType " +
            "WHERE m.uid = :id")
    Optional<List<MemberVO>> findCocktailOrPostByUid(Long id, String postType);

//    @Query("SELECT m.nickname AS nickname, m.profileImg AS profileImg, p.createTime AS createTime, p.img AS img, p1.description AS description " +
//            "FROM Member AS m JOIN Post p ON p.member.uid = m.uid and p.d_type = :postType WHERE m.uid = :id")
//    Optional<List<MemberVO>> findCommentByUid(Long uid, String postType);
}
