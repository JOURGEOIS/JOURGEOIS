package com.jourgeois.backend.api.dto.home;

public interface HomeCocktailItemVO {
    Long getCocktailId();
    String getImg();
    String getTitle();
    // 커칵이면 베이스 칵테일, 기본 칵테일이면 기주
    String getBase();
    Long getBaseCocktailId();

    Integer getType();
    // 기본 칵테일만 가짐
    Long getAbv();
}
