package com.service.cinema.repository;

import com.service.cinema.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<CityEntity,Integer> {
    @Override
    List<CityEntity> findAll();
    List<CityEntity> findAllByCountryid(Integer countryid);
    CityEntity findCityEntityById(Integer cityid);
}
