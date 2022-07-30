package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Cocktail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CocktailRepository extends JpaRepository<Cocktail, String> {
    List<Cocktail> findByNameContainingOrderByName(String name, Pageable pageable);
    Optional<Cocktail> findById(Long id);
    Optional<Cocktail> deleteById(Long id);

    @Query("SELECT c.id, c.name, c.nameKR, c.alcohol, c.cupId, c.tag, c.baseLiquor, c.category, c.recipe FROM Cocktail c WHERE c.id = :id")
    Optional<String> findCocktailById(@Param("id") Long id);

    @Query("SELECT cp.nameKR FROM Cocktail c JOIN Cup cp ON c.cupId = cp.id WHERE c.id = :id")
    Optional<String> findCocktailCupById(@Param("id") Long id);
    @Query("SELECT m.nameKR, m.img FROM Cocktail c JOIN CocktailToMaterial cm ON c.id = cm.cocktail.id JOIN Material m ON cm.material.id = m.id WHERE c.id = :id")
    Optional<ArrayList<String>> findAllMaterialsByCocktailId(@Param("id") Long id);

//    @Query("UPDATE Cocktail c SET c.name = :name, c.nameKR = :nameKR, c.alcohol = :alcohol, c.cupId = :cupId," +
//            " c.tag = :tag, c.baseLiquor = :baseLiquor, c.category = :category, c.recipe = :recipe WHERE c.id = :id")
//    Optional<Cocktail> updateCocktail(@Param("Cocktail") Cocktail cocktail);
}
