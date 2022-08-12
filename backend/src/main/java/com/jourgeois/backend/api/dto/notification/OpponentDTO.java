package com.jourgeois.backend.api.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter
@ToString
public class OpponentDTO{
    Long uid;
    String img;
    String nickname;
}
