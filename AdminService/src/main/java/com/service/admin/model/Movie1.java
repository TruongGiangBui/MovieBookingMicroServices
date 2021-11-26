package com.service.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie1 {
    private String movie_name_eng;
    private String timespan;
    private String poster_link;
    private String trailer_link;
    private String director;
    private String film_content;
    private String release_date;
}