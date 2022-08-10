package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.home.HomeCocktailItemVO;
import com.jourgeois.backend.api.dto.post.CocktailAwardsVO;
import com.jourgeois.backend.api.dto.post.NewsFeedVO;
import com.jourgeois.backend.domain.member.Member;
import com.jourgeois.backend.domain.post.Post;
import com.jourgeois.backend.util.TagType;
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


    @Query(value="select count(*) from post where p_dtype = :type and p_writer = :uid", nativeQuery = true)
    Long findByCocktailAwards(Long uid, String type);

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

    @Query(value = "select pid as cocktailId, p_img as img, cc_cocktail_title as title from\n" +
            "(select (likes + comments) as score, pid from\n" +
            "(select likes, IFNULL(comments, 0) as comments, IFNULL(p_id, pr_p_id) as pid, IFNULL(pr_p_id, p_id) from\n" +
            "(select count(*) as likes, p_id from post_bookmark where date_add(now(), interval -7 day) <= b_timestamp AND b_timestamp < now() group by p_id) as likeTable\n" +
            "left join\n" +
            "(select count(*) as comments, pr_p_id from post_review where date_add(now(), interval -7 day) <= pr_create_time AND pr_create_time < now() group by pr_p_id) as commentTable\n" +
            "on likeTable.p_id = commentTable.pr_p_id) as LeftJoin\n" +
            "union\n" +
            "select (likes + comments) as score, pid from\n" +
            "(select IFNULL(likes, 0) as likes, IFNULL(comments, 0) as comments, IFNULL(p_id, pr_p_id) as pid, IFNULL(pr_p_id, p_id) from\n" +
            "(select count(*) as likes, p_id from post_bookmark where date_add(now(), interval -7 day) <= b_timestamp AND b_timestamp < now() group by p_id) as likeTable\n" +
            "right join\n" +
            "(select count(*) as comments, pr_p_id from post_review where date_add(now(), interval -7 day) <= pr_create_time AND pr_create_time < now() group by pr_p_id) as commentTable\n" +
            "on likeTable.p_id = commentTable.pr_p_id) as RightJoin) as scoreInfo\n" +
            "left join custom_cocktail\n" +
            "on custom_cocktail.p_id = pid\n" +
            "left join post\n" +
            "on pid = post.p_id\n" +
            "where p_dtype = 'cocktail'\n" +
            "order by score desc", nativeQuery = true)
    List<HomeCocktailItemVO> getWeeklyHotCustomCocktail(Pageable pageable);

    @Query(value = "select pid as cocktailId, p_img as img, cc_cocktail_title as title from\n" +
            "(select (likes + comments) as score, pid from\n" +
            "(select likes, IFNULL(comments, 0) as comments, IFNULL(p_id, pr_p_id) as pid, IFNULL(pr_p_id, p_id) from\n" +
            "(select count(*) as likes, p_id from post_bookmark where date_add(now(), interval -7 day) <= b_timestamp AND b_timestamp < now() group by p_id) as likeTable\n" +
            "left join\n" +
            "(select count(*) as comments, pr_p_id from post_review where date_add(now(), interval -7 day) <= pr_create_time AND pr_create_time < now() group by pr_p_id) as commentTable\n" +
            "on likeTable.p_id = commentTable.pr_p_id) as LeftJoin\n" +
            "union\n" +
            "select (likes + comments) as score, pid from\n" +
            "(select IFNULL(likes, 0) as likes, IFNULL(comments, 0) as comments, IFNULL(p_id, pr_p_id) as pid, IFNULL(pr_p_id, p_id) from\n" +
            "(select count(*) as likes, p_id from post_bookmark where date_add(now(), interval -7 day) <= b_timestamp AND b_timestamp < now() group by p_id) as likeTable\n" +
            "right join\n" +
            "(select count(*) as comments, pr_p_id from post_review where date_add(now(), interval -7 day) <= pr_create_time AND pr_create_time < now() group by pr_p_id) as commentTable\n" +
            "on likeTable.p_id = commentTable.pr_p_id) as RightJoin) as scoreInfo\n" +
            "left join custom_cocktail\n" +
            "on custom_cocktail.p_id = pid\n" +
            "left join post\n" +
            "on pid = post.p_id\n" +
            "where p_dtype = 'cocktail'\n" +
            "order by score desc limit 5", nativeQuery = true)
    List<HomeCocktailItemVO> getWeeklyHot5CustomCocktail();

    @Query(value="select p.p_id as postId, p.p_img as imgLink, c.contest_title as title,\n" +
            "if((select count(*) from post_bookmark where m_id = :uid and p_id = p.p_id), 1, 0) as 'like'\n" +
            "from post as p join cocktail_awards as c\n" +
            "on p.p_id = c.p_id\n" +
            "order by p_create_time desc", nativeQuery = true)
    List<CocktailAwardsVO> getCocktailAwardsList(Long uid);

    @Query(value = "select p.p_id as postId, p.p_img as imgLink, c.contest_title as title,\n" +
            "(concat(round((select count(*) from post_bookmark where p_id = p.p_id) / \n" +
            "    (select count(m_id) from post_bookmark \n" +
            "where p_id in \n" +
            "(select p_id from post where p_dtype = \"cocktail_awards\")) *100, 1), '%')) as percentage\n" +
            "from post as p join cocktail_awards as c\n" +
            "on p.p_id = c.p_id\n" +
            "order by percentage desc", nativeQuery = true)
    List<CocktailAwardsVO> getCocktailAwardsVoteList();

    @Query(value = "SELECT c_base_liquor as base, c_img as img, c_alcohol as abv, c_name_kr as title, c_id as cocktailId FROM cocktail WHERE c_tag like concat('%', :tag, '%') limit 5", nativeQuery = true)
    List<HomeCocktailItemVO> getTag5Cocktail(@Param(value = "tag") String tagType);

    @Query("SELECT c.baseLiquor as base, c.img as img, c.alcohol as abv, c.name as title, c.id as cocktailId FROM Cocktail AS c WHERE c.tag like concat('%', :tag, '%')")
    List<HomeCocktailItemVO> getTagCocktail(@Param(value = "tag") String tagType, Pageable pageable);
}
