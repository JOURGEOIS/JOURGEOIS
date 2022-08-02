package com.jourgeois.backend.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CocktailCommentDTO {
    private Long id;
    private Long userId;
    private Long cocktailId;
    private String review;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
