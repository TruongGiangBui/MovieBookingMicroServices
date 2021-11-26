package com.service.movie.service;

import com.service.movie.dto.AddMovieForm;
import com.service.movie.entity.MovieEntity;
import com.service.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public List<MovieEntity> findAll(){
        return movieRepository.findAll();
    }
    public MovieEntity findMovieById(Integer id){
        return movieRepository.findMovieEntitiesById(id);
    }
    public void addMovie(AddMovieForm form){
        movieRepository.addMovie(form.getTitle(),form.getRuntime(),form.getPoster(),form.getTrailer(),form.getDirector(),form.getFormat(),form.getPlot(),form.getReleasedate());
    }
}
