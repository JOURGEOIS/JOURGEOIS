package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, String> {

}
