package com.jourgeois.backend.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CocktailCommentDTO {
    private Long commentId;
    private Long userId;
    private String nickname;
    private String profileImg;
    private Long cocktailId;
    private String comment;
    private Integer likes;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
