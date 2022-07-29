package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Cocktail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, String> {
    List<Cocktail> findByNameContainingOrderByName(String name, Pageable pageable);
}
