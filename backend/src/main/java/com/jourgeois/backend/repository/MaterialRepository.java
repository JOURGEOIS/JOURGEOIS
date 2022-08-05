package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.cocktail.Material;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, String> {
    Optional<Material> findById(Long id);
    List<Material> findByNameKRContainingOrNameContaining(String keyword1, String keyword2, Pageable pageable);
}
