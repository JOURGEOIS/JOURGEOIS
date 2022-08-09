package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.cocktail.CocktailBookmark;
import com.jourgeois.backend.domain.cocktail.CocktailBookmarkId;
import com.jourgeois.backend.domain.home.Clip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClipRepository extends JpaRepository<Clip, Long> {

    @Query(value = "SELECT url FROM clip order by RAND() limit 1", nativeQuery = true)
    String findRandomClip();
}
