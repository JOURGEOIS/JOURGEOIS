package com.jourgeois.backend.api.dto.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class PostSearchMaterialDTO {
    private String name;
    private String img;
}
