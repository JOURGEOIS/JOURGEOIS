package com.jourgeois.backend.service;

import com.jourgeois.backend.domain.SearchKeyword;
import com.jourgeois.backend.repository.SearchKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {
    private final SearchKeywordRepository searchKeywordRepository;

    CocktailService(SearchKeywordRepository searchKeywordRepository){
        this.searchKeywordRepository = searchKeywordRepository;
    }

    public List<SearchKeyword> searchKeywords(String name){
        return searchKeywordRepository.findByNameKrContainingOrderByNameKr(name);
    }
}
