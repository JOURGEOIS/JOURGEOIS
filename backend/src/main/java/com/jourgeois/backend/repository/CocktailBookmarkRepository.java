package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.cocktail.CocktailBookmark;
import com.jourgeois.backend.domain.cocktail.CocktailBookmarkId;
import com.jourgeois.backend.domain.member.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CocktailBookmarkRepository extends JpaRepository<CocktailBookmark, CocktailBookmarkId> {
//    Optional<CocktailBookmark> findByMemberAndCocktail(Member member, Cocktail cocktail);
//    Long countByCocktail(Cocktail cocktail);
    Long countByCocktailId(Cocktail c_id);
    List<CocktailBookmark> findByCocktailId(Cocktail c_id, Pageable pageable);
}
