package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.CocktailCommentVO;
import com.jourgeois.backend.domain.CocktailReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface CocktailReviewRepository extends JpaRepository<CocktailReviews, Long> {

    @Query("SELECT cr.id AS id, cr.createdDate AS createdDate, cr.modifiedDate AS modifiedDate, cr.review AS review," +
            "cr.cocktail.id as cocktailId, cr.member.uid as userId FROM CocktailReviews AS cr WHERE cr.cocktail.id = :id")
    Optional<ArrayList<CocktailCommentVO>> findBycId(@Param("id") Long id);

}
