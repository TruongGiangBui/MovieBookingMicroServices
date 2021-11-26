package com.service.cinema.repository;


import com.service.cinema.entity.CountryEntity;
import com.service.cinema.entity.NowShowingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NowShowingRepository extends JpaRepository<NowShowingEntity,Integer> {
    @Override
    List<NowShowingEntity> findAll();
    List<NowShowingEntity> findAllByCinemaid(Integer cinemaid);
}
