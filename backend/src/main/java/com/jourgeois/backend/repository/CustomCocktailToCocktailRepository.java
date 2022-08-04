package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.post.CustomCocktailVO;
import com.jourgeois.backend.api.dto.post.PostInfoDTO;
import com.jourgeois.backend.domain.post.CustomCocktailId;
import org.springframework.data.domain.Pageable;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.post.CustomCocktail;
import com.jourgeois.backend.domain.post.CustomCocktailToCocktail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomCocktailToCocktailRepository extends JpaRepository<CustomCocktailToCocktail, CustomCocktailId> {
//    Optional<CustomCocktailToCocktail> findByCustomCocktailAndCocktail(CustomCocktail customCocktail, Cocktail cocktail);
//    Optional<CustomCocktailToCocktail> findByCustomCocktail_Id(Long id);
//    List<CustomCocktailToCocktail> findByCocktail_Id(Long id, Pageable pageable);

    Optional<CustomCocktailToCocktail> findByCustomCocktailId(CustomCocktail customCocktail);
    void deleteByCustomCocktailId(CustomCocktail id);


    @Query(value = "select m.uid as uid, m.nickname as nickname, m.profile_img as profileImg\n" +
            ",result.cc_cocktail_ingredients as postIngredients, result.cc_cocktail_recipe as postRecipe, result.cc_cocktail_title as postTitle,\n" +
            "result.p_id as postId, result.p_img as postImg, result.p_create_time as postCreateTime,\n" +
            "result.p_last_update_time as postLastUpdateTime, result.p_description as postDescription,\n" +
            "ifnull(result.count, 0) as postCount\n" +
            "from member as m join\n" +
            "(select test1.*, test2.* from \n" +
            "(select c.*, p.p_img, p.p_create_time, p.p_last_update_time, p.p_description, p.p_writer from custom_cocktail c left join post p\n" +
            "on c.p_id = p.p_id\n" +
            "where p.p_id in (select c.p_id from custom_cocktail_to_cocktail c where c_id = :id)) as test1 left join \n" +
            "(select count(*) as count, p_id as pp from post_bookmark group by p_id having p_id in (select c.p_id from custom_cocktail_to_cocktail c where c_id = :id)) as test2 \n" +
            "on test1.p_id = test2.pp) as result \n" +
            "on m.uid=result.p_writer order by result.p_create_time desc", nativeQuery = true)
    List<CustomCocktailVO> findByCustomCocktailListOrderbyCreateTime(@Param(value="id") Long id);

    @Query(value = "select m.uid as uid, m.nickname as nickname, m.profile_img as profileImg\n" +
            ",result.cc_cocktail_ingredients as postIngredients, result.cc_cocktail_recipe as postRecipe, result.cc_cocktail_title as postTitle,\n" +
            "result.p_id as postId, result.p_img as postImg, result.p_create_time as postCreateTime,\n" +
            "result.p_last_update_time as postLastUpdateTime, result.p_description as postDescription,\n" +
            "ifnull(result.count, 0) as postCount\n" +
            "from member as m join\n" +
            "(select test1.*, test2.* from \n" +
            "(select c.*, p.p_img, p.p_create_time, p.p_last_update_time, p.p_description, p.p_writer from custom_cocktail c left join post p\n" +
            "on c.p_id = p.p_id\n" +
            "where p.p_id in (select c.p_id from custom_cocktail_to_cocktail c where c_id = :id)) as test1 left join \n" +
            "(select count(*) as count, p_id as pp from post_bookmark group by p_id having p_id in (select c.p_id from custom_cocktail_to_cocktail c where c_id = :id)) as test2 \n" +
            "on test1.p_id = test2.pp) as result \n" +
            "on m.uid=result.p_writer order by result.count desc", nativeQuery = true)
    List<CustomCocktailVO> findByCustomCocktailListOrderbyCount(@Param(value="id") Long id);
}
