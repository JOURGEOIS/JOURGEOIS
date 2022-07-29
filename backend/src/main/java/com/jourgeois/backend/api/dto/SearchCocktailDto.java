package com.jourgeois.backend.api.dto;

import com.jourgeois.backend.domain.Cocktail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder @Getter @Setter
public class SearchCocktailDto {
    // uid, 이름, 도수, 이미지, 기주
    private Long id;
    private String name;
    private double alcohol;
    private String baseLiquor;

}
