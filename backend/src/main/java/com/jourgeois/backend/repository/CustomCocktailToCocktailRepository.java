package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.CustomCocktail;
import com.jourgeois.backend.domain.CustomCocktailToCocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomCocktailToCocktailRepository extends JpaRepository<CustomCocktailToCocktail, Long> {
    Optional<CustomCocktailToCocktail> findByCustomCocktailAndCocktail(CustomCocktail customCocktail, Cocktail cocktail);
}
