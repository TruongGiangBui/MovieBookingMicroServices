package com.service.cinema.service;


import com.service.cinema.entity.*;
import com.service.cinema.model.Cinema;
import com.service.cinema.model.Movie;
import com.service.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class CinemaService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private NowShowingRepository nowShowingRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    public List<CountryEntity> getAllCountries(){
        return countryRepository.findAll();
    }

    public List<CityEntity> getCitiesByCountryId(Integer countryid){
        return cityRepository.findAllByCountryid(countryid);
    }

    public List<Cinema> getCinemasByCityId(Integer cityid){
        List<Cinema> cinemas=new ArrayList<>();
        for(CinemaEntity cinemaEntity:cinemaRepository.findAllByCityid(cityid)){
            cinemas.add(Cinema.getModel(cinemaEntity));
        }
        return cinemas;
    }


    public List<Movie> getAllNowShowingMovies(Integer cinemaid,Integer day) throws ParseException {
        Date date=new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);

        List<NowShowingEntity> nowShowingEntities=nowShowingRepository.findAllByCinemaidAndSdate(cinemaid,new Date(c.getTimeInMillis()));
        List<Movie> movies=new ArrayList<>();
        for(NowShowingEntity nowShowingEntity:nowShowingEntities){
            Movie movie=restTemplate.getForObject("http://movies-service/api/movies/{id}",Movie.class,nowShowingEntity.getMovieid());
            movies.add(movie);
        }
        return movies;
    }


    public void fakeCountry(){
        List<String> list= Arrays.asList(
                "Đà Nẵng",
                "Cần Thơ",
                "Đồng Nai",
                "Hải Phòng",
                "Quảng Ninh",
                "Bà Rịa-Vũng Tàu",
                "Bình Định",
                "Bình Dương",
                "Đắk Lắk",
                "Trà Vinh",
                "Yên Bái",
                "Vĩnh Long",
                "Kiên Giang",
                "Hậu Giang",
                "Hà Tĩnh",
                "Phú Yên",
                "Đồng Tháp",
                "Hưng Yên",
                "Khánh Hòa",
                "Kon Tum",
                "Lạng Sơn",
                "Nghệ An",
                "Quảng Ngãi",
                "Sóc Trăng",
                "Sơn La",
                "Tây Ninh",
                "Thái Nguyên",
                "Tiền Giang"
        );
        int id=3;
        for(String country:list){
            CityEntity cityEntity=new CityEntity();
            cityEntity.setId(id);
            cityEntity.setCountryid(1);
            cityEntity.setName(country);
            cityRepository.save(cityEntity);
            id++;
        }
    }
}
