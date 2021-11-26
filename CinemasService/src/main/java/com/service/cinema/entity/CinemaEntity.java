package com.service.cinema.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="cinemas")
public class CinemaEntity {
    @Id
    private Integer id;
    private String name;
    @Column(name = "city_id")
    private Integer cityid;
    @OneToMany(mappedBy = "cinemaEntity",fetch = FetchType.LAZY)
    private Set<ScheduleEntity> scheduleEntities;
}
