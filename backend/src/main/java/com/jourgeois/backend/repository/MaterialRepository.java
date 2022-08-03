package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.cocktail.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, String> {
    Optional<Material> findById(Long id);

}
