package com.service.booking.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
