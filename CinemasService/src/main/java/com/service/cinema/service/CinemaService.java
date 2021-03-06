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
                "???? N???ng",
                "C???n Th??",
                "?????ng Nai",
                "H???i Ph??ng",
                "Qu???ng Ninh",
                "B?? R???a-V??ng T??u",
                "B??nh ?????nh",
                "B??nh D????ng",
                "?????k L???k",
                "Tr?? Vinh",
                "Y??n B??i",
                "V??nh Long",
                "Ki??n Giang",
                "H???u Giang",
                "H?? T??nh",
                "Ph?? Y??n",
                "?????ng Th??p",
                "H??ng Y??n",
                "Kh??nh H??a",
                "Kon Tum",
                "L???ng S??n",
                "Ngh??? An",
                "Qu???ng Ng??i",
                "S??c Tr??ng",
                "S??n La",
                "T??y Ninh",
                "Th??i Nguy??n",
                "Ti???n Giang"
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
