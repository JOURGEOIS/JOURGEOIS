package com.jourgeois.backend.api.dto;

import lombok.*;

import java.util.ArrayList;

@Getter @Setter
@Builder
@ToString
@AllArgsConstructor
public class CocktailVO {

    private String id;
    private String name;
    private String nameKR;
    private Double alcohol;
    private String cupName;
    private String tag;
    private String baseLiquor;
    private String category;
    private String recipe;
    private String img;
    private ArrayList<String> materials;
}
