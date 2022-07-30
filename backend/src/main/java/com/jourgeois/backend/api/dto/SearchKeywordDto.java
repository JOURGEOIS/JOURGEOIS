package com.jourgeois.backend.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SearchKeywordDto {
    private Long id;
    private String name;
    private String nameKr;
    private String type;
}
