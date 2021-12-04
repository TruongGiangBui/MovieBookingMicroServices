package com.service.cinema.service;

import com.service.cinema.entity.ScheduleEntity;
import com.service.cinema.dto.AddScheduleForm;
import com.service.cinema.model.Schedule;
import com.service.cinema.repository.NowShowingRepository;
import com.service.cinema.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private NowShowingRepository nowShowingRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    public List<Schedule> getAllSchedulesByCinemaId(Integer cinemaid,Integer day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long nowmillis=System.currentTimeMillis();
        long days=nowmillis/86400000;
        Timestamp start= new Timestamp(calendar.getTimeInMillis()+day*86400000);
        Timestamp end=new Timestamp(calendar.getTimeInMillis()+(day+1)* 86400000L);
        if(day==0){
            start=new Timestamp(nowmillis);
        }
        List<Schedule> schedules=new ArrayList<>();
        List<ScheduleEntity> scheduleEntities=scheduleRepository.findAllByCinemaidAndStarttimeBetween(cinemaid,start,end);
        for(ScheduleEntity scheduleEntity:scheduleEntities){
            schedules.add(Schedule.convertEntity(scheduleEntity));
        }
        return schedules;
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long nowmillis=System.currentTimeMillis();
        long days=nowmillis/86400000;
        Timestamp start= new Timestamp(calendar.getTimeInMillis()+day*86400000);
        Timestamp end=new Timestamp(calendar.getTimeInMillis()+(day+1)* 86400000L);
        if(day==0){
            start=new Timestamp(nowmillis);
        }
        List<Schedule> schedules=new ArrayList<>();
        List<ScheduleEntity> scheduleEntities=scheduleRepository.findAllByCinemaidAndMovieidAndStarttimeBetween(cinemaid,movieid,start,end);

        for(ScheduleEntity scheduleEntity:scheduleEntities){
            schedules.add(Schedule.convertEntity(scheduleEntity));
        }
        return schedules;
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
