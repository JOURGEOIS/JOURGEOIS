package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.post.cocktail.CocktailCommentVO;
import com.jourgeois.backend.api.dto.post.cocktail.CocktailVO;
import com.jourgeois.backend.domain.cocktail.CocktailComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CocktailCommentRepository extends JpaRepository<CocktailComment, Long> {

    @Query("SELECT cr.commentId AS commentId, cr.member.uid AS userId, cr.member.nickname AS nickname, cr.member.profileImg AS profileImg," +
            " cr.cocktail.id AS cocktailId, cr.createdDate AS createdDate, cr.modifiedDate AS modifiedDate, cr.comment AS comment " +
            "FROM CocktailComment AS cr WHERE cr.cocktail.id = :id ORDER BY cr.createdDate DESC")
    Optional<ArrayList<CocktailCommentVO>> findCommentsByCocktailId(@Param("id") Long id, Pageable pageable);

    Optional<CocktailComment> findCocktailCommentByCommentId(@Param("id") Long id);

    @Query("SELECT cm FROM CocktailComment cm WHERE cm.member.uid = :uId AND cm.commentId = :commentId")
    Optional<CocktailComment> findByUidAndCommentId(@Param("uId") Long uId, @Param("commentId") Long commentId);

    @Query("SELECT c.id AS id, c.nameKR AS nameKR, c.img AS img, c.category AS category, c.tag AS tag, cc.comment AS comment " +
            "FROM Member AS m JOIN CocktailComment AS cc ON cc.member.uid = m.uid JOIN Cocktail AS c ON cc.cocktail.id = c.id " +
            "WHERE m.uid = :id")
    Optional<List<CocktailVO>> findCocktailCommentsInProfilePageByUid(Long id);
}
