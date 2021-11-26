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
@Table(name="cinemas_now_showing")
public class NowShowingEntity {
    @Id
    private Integer id;
    @Column(name="cinema_id")
    private Integer cinemaid;
    @Column(name = "movie_id")
    private Integer movieid;
}
