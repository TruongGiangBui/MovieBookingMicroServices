package com.service.cinema.service;

import com.service.cinema.entity.NowShowingEntity;
import com.service.cinema.entity.ScheduleEntity;
import com.service.cinema.model.AddScheduleForm;
import com.service.cinema.model.Movie;
import com.service.cinema.model.Schedule;
import com.service.cinema.repository.NowShowingRepository;
import com.service.cinema.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private NowShowingRepository nowShowingRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    public List<Schedule> getAllSchedulesByCinemaId(Integer cinemaid){
        List<Schedule> schedules=new ArrayList<>();
        List<ScheduleEntity> scheduleEntities=scheduleRepository.findAllByCinemaid(cinemaid);
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
                scheduleEntity.getSeats());
    }
    public List<Schedule> getNowShowingMoviesSchedules(Integer cinemaid, Integer movieid){
        List<Schedule> schedules=new ArrayList<>();
        List<ScheduleEntity> scheduleEntities=scheduleRepository.findAllByCinemaidAndMovieidAndStarttimeGreaterThanEqual(cinemaid,movieid,new Timestamp(System.currentTimeMillis()));

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
        scheduleRepository.updateSeats(scheduleid,schedule.toEntity().getSeats());
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
        scheduleRepository.updateSeats(scheduleid,schedule.toEntity().getSeats());
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
        scheduleRepository.updateSeats(scheduleid,schedule.toEntity().getSeats());
    }
}
