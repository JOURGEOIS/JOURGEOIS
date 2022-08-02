package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.CocktailVO;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.CocktailReviews;
import com.jourgeois.backend.domain.Cup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CocktailReviewRepository extends JpaRepository<CocktailReviews, Long> {

    Boolean findByCocktailId(@Param("id") String id);

}
