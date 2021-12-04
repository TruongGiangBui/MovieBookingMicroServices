package com.service.cinema.controller;

import com.service.cinema.entity.CityEntity;
import com.service.cinema.entity.CountryEntity;
import com.service.cinema.model.Cinema;
import com.service.cinema.model.Movie;
import com.service.cinema.model.Schedule;
import com.service.cinema.service.CinemaService;
import com.service.cinema.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("countries")
    @ResponseBody
    public ResponseEntity<List<CountryEntity>> getAllCountries(){
        return new ResponseEntity<>(cinemaService.getAllCountries(),HttpStatus.OK);
    }

    @GetMapping("cities")
    @ResponseBody
    public ResponseEntity<List<CityEntity>> getCitiesByCountryId(@RequestParam(value = "countryid",required = true) Integer countryid){
        return new ResponseEntity<>(cinemaService.getCitiesByCountryId(countryid),HttpStatus.OK);
    }

    @GetMapping("cinemas")
    @ResponseBody
    public ResponseEntity<List<Cinema>> getCinemasByCityId(@RequestParam(value = "cityid",required = true)Integer cityid){
        return new ResponseEntity<>(cinemaService.getCinemasByCityId(cityid),HttpStatus.OK);
    }

    @GetMapping("cinemas/{cinemaid}/schedules")
    @ResponseBody
    public ResponseEntity<List<Schedule>> getAllSchedulesByCinemaId(@PathVariable(name = "cinemaid") Integer cinemaid,
                                                                    @RequestParam(value = "day",required = false) Integer dayfromnow,
                                                                    @RequestParam(value = "movieid",required = false) Integer movieid){
        if(dayfromnow==null) dayfromnow=0;
        if(movieid==null)
            return new ResponseEntity<>(scheduleService.getAllSchedulesByCinemaId(cinemaid,dayfromnow),HttpStatus.OK);
        else return new ResponseEntity<>(scheduleService.getNowShowingMoviesSchedules(cinemaid,movieid,dayfromnow),HttpStatus.OK);
    }

    @GetMapping("cinemas/{cinemaid}/movies/now-showing")
    @ResponseBody
    public ResponseEntity<List<Movie>> getAllNowShowingMovies(@PathVariable(name = "cinemaid") Integer cinemaid){
        return new ResponseEntity<>(cinemaService.getAllNowShowingMovies(cinemaid),HttpStatus.OK);
    }

    @GetMapping("cinemas/schedules/{scheduleid}")
    @ResponseBody
    public ResponseEntity<Schedule> getScheduleById(@PathVariable(name = "scheduleid") Integer scheduleid){

        return new ResponseEntity<>(scheduleService.getScheduleById(scheduleid),HttpStatus.OK);
    }
}
