package com.jourgeois.backend.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class SearchCocktailDTO {
    // uid, 이름, 도수, 이미지, 기주
    private Long id;
    private String img;
    private String name;
    private double alcohol;
    private String baseLiquor;

}
