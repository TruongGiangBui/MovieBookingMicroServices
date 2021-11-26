package com.service.cinema.repository;


import com.service.cinema.entity.CinemaEntity;
import com.service.cinema.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CountryRepository extends JpaRepository<CountryEntity,Integer> {
    @Override
    List<CountryEntity> findAll();
    CountryEntity findCountryEntityById(Integer id);
}
