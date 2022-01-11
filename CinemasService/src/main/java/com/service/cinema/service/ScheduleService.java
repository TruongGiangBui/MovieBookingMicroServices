package com.service.cinema.service;

import com.service.cinema.entity.ScheduleEntity;
import com.service.cinema.dto.AddScheduleForm;
import com.service.cinema.model.Schedule;
import com.service.cinema.repository.NowShowingRepository;
import com.service.cinema.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleService {
    @Autowired
    private NowShowingRepository nowShowingRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    public List<Schedule> getAllSchedulesByCinemaId(Integer cinemaid,Integer day)  {

        try{
            long nowmillis=System.currentTimeMillis();
            SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
            long beginday=format.parse(String.format("%4d%02d%02d",LocalDateTime.now().getYear(),LocalDateTime.now().getMonthValue(),LocalDateTime.now().getDayOfMonth())).getTime();
            Timestamp start= new Timestamp(beginday+day*86400000L);
            Timestamp end=new Timestamp(beginday+(day+1)* 86400000L);
            if(day==0){
                start=new Timestamp(nowmillis);
            }
            List<Schedule> schedules=new ArrayList<>();
            List<ScheduleEntity> scheduleEntities=scheduleRepository.findAllByCinemaidAndStarttimeBetweenOrderByStarttime(cinemaid,start,end);
            for(ScheduleEntity scheduleEntity:scheduleEntities){
                schedules.add(Schedule.convertEntity(scheduleEntity));
            }
            return schedules;
        }catch (Exception e){
            return null;
        }
    }
    public Schedule getScheduleById(Integer id){
        return Schedule.convertEntity(scheduleRepository.findScheduleEntityById(id));
    }

    public void addSchedule(AddScheduleForm addScheduleForm){

        ScheduleEntity scheduleEntity=addScheduleForm.toEntity();
        scheduleRepository.insertSchedule(scheduleEntity.getCinemaroom(),
                scheduleEntity.getMovieid(),
                scheduleEntity.getCinemaid(),
                scheduleEntity.getStarttime(),
                scheduleEntity.getEndtime(),
                scheduleEntity.getCapacity(),
                scheduleEntity.getSeats(),
                scheduleEntity.getPrice());
    }
    public List<Schedule> getNowShowingMoviesSchedules(Integer cinemaid, Integer movieid,Integer day){

        try {
            long nowmillis = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            long beginday = format.parse(String.format("%4d%02d%02d", LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth())).getTime();
            Timestamp start = new Timestamp(beginday + day * 86400000L);
            Timestamp end = new Timestamp(beginday + (day + 1) * 86400000L);
            if (day == 0) {
                start = new Timestamp(nowmillis);
            }
            List<Schedule> schedules = new ArrayList<>();
            List<ScheduleEntity> scheduleEntities = scheduleRepository.findAllByCinemaidAndMovieidAndStarttimeBetweenOrderByStarttime(cinemaid, movieid, start, end);

            for (ScheduleEntity scheduleEntity : scheduleEntities) {
                schedules.add(Schedule.convertEntity(scheduleEntity));
            }
            return schedules;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void selectSeats(Integer scheduleid,List<Integer> seats) throws Exception {
        Schedule schedule=getScheduleById(scheduleid);
        for(Integer seat:seats){
            if(!schedule.getSeatslist().get(seat).equals("0")){
                throw new Exception("Seats were selected");
            }
        }

        for(Integer seat:seats){
                schedule.getSeatslist().set(seat,"2");
        }
//        scheduleRepository.updateSeats(scheduleid,schedule.toEntity().getSeats());
        scheduleRepository.saveAndFlush(schedule.toEntity());
    }
    public void orderSeats(Integer scheduleid,List<Integer> seats) throws Exception {
        Schedule schedule=getScheduleById(scheduleid);
        for(Integer seat:seats){
            if(!schedule.getSeatslist().get(seat).equals("2")){
                throw new Exception("Seats were selected");
            }
        }
        for(Integer seat:seats) {
            schedule.getSeatslist().set(seat, "1");
        }
//        scheduleRepository.updateSeats(scheduleid,schedule.toEntity().getSeats());
        scheduleRepository.saveAndFlush(schedule.toEntity());
    }
    public void unselectSeats(Integer scheduleid,List<Integer> seats) throws Exception {
        Schedule schedule=getScheduleById(scheduleid);
        for(Integer seat:seats){
            if(!schedule.getSeatslist().get(seat).equals("2")){
                throw new Exception("Seats were ordered");
            }
        }
        for(Integer seat:seats){
            schedule.getSeatslist().set(seat,"0");
        }
//        scheduleRepository.updateSeats(scheduleid,schedule.toEntity().getSeats());
        scheduleRepository.saveAndFlush(schedule.toEntity());
    }
}
