package com.jourgeois.backend.repository;

import org.springframework.data.domain.Pageable;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.post.CustomCocktail;
import com.jourgeois.backend.domain.post.CustomCocktailToCocktail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomCocktailToCocktailRepository extends JpaRepository<CustomCocktailToCocktail, Long> {
    Optional<CustomCocktailToCocktail> findByCustomCocktailAndCocktail(CustomCocktail customCocktail, Cocktail cocktail);
    Optional<CustomCocktailToCocktail> findByCustomCocktail_Id(Long id);
    List<CustomCocktailToCocktail> findByCocktail_Id(Long id, Pageable pageable);
}
