package com.service.cinema.repository;


import com.service.cinema.entity.CountryEntity;
import com.service.cinema.entity.NowShowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface NowShowingRepository extends JpaRepository<NowShowingEntity,Integer> {
    @Override
    List<NowShowingEntity> findAll();
    List<NowShowingEntity> findAllByCinemaidAndSdate(Integer cinemaid,Date date);
    NowShowingEntity getDistinctFirstByCinemaidAndMovieidAndSdate(Integer cinema, Integer movie, Date date);
    boolean existsByCinemaidAndMovieidAndSdate(Integer cinema, Integer movie, Date date);
}
