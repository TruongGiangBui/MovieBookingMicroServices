package com.service.cinema.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="cities")
public class CityEntity {
    @Id
    private Integer id;
    private String name;
    @Column(name="country_id")
    private Integer countryid;
}
