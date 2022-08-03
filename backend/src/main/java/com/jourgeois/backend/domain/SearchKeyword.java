package com.jourgeois.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SearchKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uid;
    private Long id;
    private String name;
    private String nameKr;
    private String type;
}
