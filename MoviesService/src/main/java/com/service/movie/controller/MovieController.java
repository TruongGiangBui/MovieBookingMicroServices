package com.service.movie.controller;

import com.service.movie.dto.AddMovieForm;
import com.service.movie.entity.MovieEntity;
import com.service.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RibbonClient(name = "movies-service")
@CrossOrigin
public class MovieController {
    private MovieService movieService;
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping("api/movies")
    @ResponseBody
    public ResponseEntity<List<MovieEntity>> findAllMovies(){
        return new ResponseEntity<>(movieService.findAll(), HttpStatus.OK);
    }
    @GetMapping("api/movies/{id}")
    @ResponseBody
    public ResponseEntity<MovieEntity> findMoviebyId(@PathVariable("id") Integer id){
        return new ResponseEntity<>(movieService.findMovieById(id),HttpStatus.OK);
    }
    @PostMapping("api/movies")
    @ResponseBody
    public ResponseEntity<String> addMovie(@RequestBody AddMovieForm form){
        try{
            movieService.addMovie(form);
            System.out.println(form.toString());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
