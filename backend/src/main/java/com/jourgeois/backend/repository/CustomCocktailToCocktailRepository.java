package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.cocktail.Cocktail;
import com.jourgeois.backend.domain.post.CustomCocktail;
import com.jourgeois.backend.domain.post.CustomCocktailToCocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomCocktailToCocktailRepository extends JpaRepository<CustomCocktailToCocktail, Long> {
    Optional<CustomCocktailToCocktail> findByCustomCocktailAndCocktail(CustomCocktail customCocktail, Cocktail cocktail);
    Optional<CustomCocktailToCocktail> findByCustomCocktail_Id(Long id);
}
