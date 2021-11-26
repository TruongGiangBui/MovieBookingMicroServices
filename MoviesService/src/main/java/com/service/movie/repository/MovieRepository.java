package com.service.movie.repository;

import com.service.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Integer> {
    List<MovieEntity> findAll();
    MovieEntity findMovieEntitiesById(Integer id);
    @Transactional
    @Modifying
    @Query(value = "insert into movies(title,runtime,poster,trailer,director,format,plot,release_date)" +
            " values(:title,:runtime,:poster,:trailer,:director,:format,:plot,:releasedate)",nativeQuery = true)
    void addMovie(@Param("title") String title,
                  @Param("runtime")BigDecimal runtime,
                  @Param("poster") String poster,
                  @Param("trailer") String trailer,
                  @Param("director") String director,
                  @Param("format") String format,
                  @Param("plot") String plot,
                  @Param("releasedate") Date date);
}
