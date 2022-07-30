package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MaterialRepository extends JpaRepository<Cocktail, String> {

}
