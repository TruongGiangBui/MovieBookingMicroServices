package com.service.cinema.model;

import com.service.cinema.entity.CinemaEntity;
import com.service.cinema.entity.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    private Integer id;
    private Integer cinemaroom;
    private Integer cinemaid;
    private Integer movieid;
    private Timestamp starttime;
    private Timestamp endtime;
    private BigDecimal capacity;
    private List<String> seatslist;
    private BigDecimal price;
    public static Schedule convertEntity(ScheduleEntity scheduleEntity){
        ModelMapper modelMapper =new ModelMapper();
        Schedule schedule=modelMapper.map(scheduleEntity,Schedule.class);
        String[] seats=scheduleEntity.getSeats().split(",");
        schedule.setSeatslist(Arrays.asList(seats));
        return schedule;
    }
    public ScheduleEntity toEntity(){
        ModelMapper modelMapper =new ModelMapper();
        ScheduleEntity scheduleEntity=modelMapper.map(this,ScheduleEntity.class);
        scheduleEntity.setSeats(StringUtils.join(seatslist, ","));
        return scheduleEntity;
    }
}
