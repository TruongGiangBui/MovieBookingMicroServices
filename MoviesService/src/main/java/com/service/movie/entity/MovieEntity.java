package com.service.movie.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="movies")
public class MovieEntity {
    @Id
    private Integer id;
    private String title;
    private BigDecimal runtime;
    private String poster;
    private String trailer;
    private String director;
    private String format;
    private String plot;
    @Column(name = "release_date")
    private Date releasedate;
}
