package com.jourgeois.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Cup {
    @Id
    @GeneratedValue
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_name")
    private String name;

    @Column(name = "c_name_kr")
    private String nameKr;
}