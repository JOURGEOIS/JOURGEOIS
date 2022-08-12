package com.jourgeois.backend.api.dto.notification;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class OpponentDTO {
    Long uid;
    String img;
    String nickname;
}
