package com.service.cinema.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="schedules")
public class ScheduleEntity {
    @Id
    private Integer id;
    @Column(name ="cinema_room")
    private Integer cinemaroom;
    @Column(name="cinema_id")
    private Integer cinemaid;
    @Column(name="movie_id")
    private Integer movieid;
    @Column(name="start_time")
    private Timestamp starttime;
    @Column(name="end_time")
    private Timestamp endtime;
    private BigDecimal capacity;
    private String seats;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cinema_id",referencedColumnName = "id",insertable=false, updatable=false)
    private CinemaEntity cinemaEntity;
}
