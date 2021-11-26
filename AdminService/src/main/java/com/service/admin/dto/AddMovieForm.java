package com.service.admin.dto;

import com.service.admin.model.Movie1;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddMovieForm {
    private String title;
    private BigDecimal runtime;
    private String poster;
    private String trailer;
    private String director;
    private String format;
    private String plot;
    private Date releasedate;
    public AddMovieForm(Movie1 movie1) throws ParseException {
        title=movie1.getMovie_name_eng();
        runtime=new BigDecimal(movie1.getTimespan().replace(" phút",""));
        poster=movie1.getPoster_link().replace("/appdata/filmposters/","");
        trailer=movie1.getTrailer_link();
        director=movie1.getDirector();
        format="IMAX";
        plot=movie1.getFilm_content();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        releasedate=new Date(format.parse(movie1.getRelease_date()).getTime());
    }
}
