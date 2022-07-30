package com.jourgeois.backend.api.dto;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Getter @Setter
@Builder
@ToString
public class CocktailDTO {

    private String id;
    private String name;
    private String nameKR;
    private String alcohol;
    private String cupName;
    private String tag;
    private String baseLiquor;
    private String category;
    private String recipe;
    private String img;
    private ArrayList<String> materials;
}
