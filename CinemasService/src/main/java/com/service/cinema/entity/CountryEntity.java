package com.service.cinema.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="countries")
public class CountryEntity {
    @Id
    private Integer id;
    private String name;
}
