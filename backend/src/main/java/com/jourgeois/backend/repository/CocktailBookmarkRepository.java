package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.cocktail.CocktailBookmark;
import com.jourgeois.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CocktailBookmarkRepository extends JpaRepository<CocktailBookmark, Long> {
    Optional<CocktailBookmark> findByMemberAndCocktail(Member member, Cocktail cocktail);
    Long countByCocktail(Cocktail cocktail);
}
