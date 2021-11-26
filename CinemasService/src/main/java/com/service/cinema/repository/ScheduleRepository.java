package com.service.cinema.repository;

import com.service.cinema.entity.CityEntity;
import com.service.cinema.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Integer> {
    ScheduleEntity findScheduleEntityById(Integer id);
    List<ScheduleEntity> findAllByCinemaid(Integer cinemaid);
    List<ScheduleEntity> findAllByCinemaidAndMovieidAndStarttimeGreaterThanEqual(Integer cinemaid,Integer movieid,Timestamp time);
    @Transactional
    @Modifying
    @Query(value = "insert into schedules(cinema_room,movie_id,cinema_id,start_time,end_time,capacity,seats) " +
            "values (:room,:movieid,:cinemaid,:start,:end,:capacity,:seats);",nativeQuery = true)
    void insertSchedule(@Param("room") Integer room,
                        @Param("movieid") Integer movieid,
                        @Param("cinemaid") Integer cinemaid,
                        @Param("start") Timestamp start,
                        @Param("end") Timestamp end,
                        @Param("capacity")BigDecimal capacity,
                        @Param("seats") String seats);
    @Transactional
    @Modifying
    @Query(value = "update schedules " +
            "set seats=:seats where id=:id",nativeQuery = true)
    void updateSeats(@Param("id") Integer id,@Param("seats") String seats);
}
