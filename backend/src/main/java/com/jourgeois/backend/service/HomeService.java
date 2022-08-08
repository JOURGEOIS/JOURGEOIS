package com.jourgeois.backend.service;

import com.jourgeois.backend.api.dto.search.SearchCocktailDTO;
import com.jourgeois.backend.repository.CocktailRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private final CocktailRepository cocktailRepository;

    public HomeService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public List<SearchCocktailDTO> recommenderLiquor(Long uid, Pageable pageable){
        List<SearchCocktailDTO> list = new ArrayList<>();
        cocktailRepository.findByrecommenderLiquor(uid, pageable).forEach(data -> {
            list.add(SearchCocktailDTO.builder()
                    .id(data.getId())
                    .img(data.getImg())
                    .name(data.getNameKR())
                    .alcohol(data.getAlcohol())
                    .baseLiquor(data.getBaseLiquor()).build());
        });
        return list;
    }
}
