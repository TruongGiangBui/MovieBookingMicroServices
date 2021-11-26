package com.service.cinema.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    private Integer id;
    private String title;
    private BigDecimal runtime;
    private String poster;
    private String trailer;
    private String director;
    private String format;
    private String plot;
    private Date releasedate;
}
