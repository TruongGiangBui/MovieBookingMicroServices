package com.service.cinema.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="cinemas_now_showing")
@Builder
public class NowShowingEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="cinema_id")
    private Integer cinemaid;
    @Column(name = "movie_id")
    private Integer movieid;
    private Date sdate;
}
