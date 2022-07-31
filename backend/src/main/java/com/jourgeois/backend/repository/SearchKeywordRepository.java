package com.jourgeois.backend.repository;

import com.jourgeois.backend.domain.SearchKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, String> {
    List<SearchKeyword> findTop5ByNameKrContainingOrderByNameKr(String name);
}
