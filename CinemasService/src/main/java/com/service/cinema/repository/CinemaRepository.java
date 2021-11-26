package com.service.cinema.repository;


import com.service.cinema.entity.CinemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CinemaRepository extends JpaRepository<CinemaEntity,Integer> {
    @Override
    List<CinemaEntity> findAll();
    List<CinemaEntity> findAllByCityid(Integer cityid);
}
