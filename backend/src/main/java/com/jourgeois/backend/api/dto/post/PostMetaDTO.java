package com.jourgeois.backend.api.dto.post;

public interface PostMetaDTO {
    // Post: 일반 게시물, cocktail: 커칵, 슈커칵
    String getType();
    // 1 : 슈컥칵, 0 : 커칵
    Integer getIsSuperCustom();
    Long getPostId();
    // 커칵의 경우 베이스 칵테일 ID
    Long getBaseCocktailId();
}
