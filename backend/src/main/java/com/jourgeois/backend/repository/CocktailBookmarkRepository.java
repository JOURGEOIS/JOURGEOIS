package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.CocktailBookmark;
import com.jourgeois.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CocktailBookmarkRepository extends JpaRepository<CocktailBookmark, Long> {
    Optional<CocktailBookmark> findByMemberAndCocktail(Member member, Cocktail cocktail);
    Long countByCocktail(Cocktail cocktail);
}
