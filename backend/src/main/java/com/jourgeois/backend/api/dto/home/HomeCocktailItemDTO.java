package com.jourgeois.backend.api.dto.home;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class HomeCocktailItemDTO {
    Integer type;
    Long cocktailId;
    String img;
    String title;
    Long baseCocktailId;
    String base;
    Long abv;
}
