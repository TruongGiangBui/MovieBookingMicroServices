package com.service.cinema.controller;

import com.service.cinema.model.Schedule;
import com.service.cinema.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeatController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("cinemas/schedules/{scheduleid}/select")
    @ResponseBody
    public ResponseEntity<String> selectSeats(@PathVariable(name = "scheduleid") Integer scheduleid,
                                                    @RequestBody List<Integer> selected){
        try{
            scheduleService.selectSeats(scheduleid,selected);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("cinemas/schedules/{scheduleid}/order")
    @ResponseBody
    public ResponseEntity<String> orderSeats(@PathVariable(name = "scheduleid") Integer scheduleid,
                                              @RequestBody List<Integer> selected){
        try{
            scheduleService.orderSeats(scheduleid,selected);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("cinemas/schedules/{scheduleid}/unselect")
    @ResponseBody
    public ResponseEntity<String> unselectSeats(@PathVariable(name = "scheduleid") Integer scheduleid,
                                             @RequestBody List<Integer> selected){
        try{
            scheduleService.unselectSeats(scheduleid,selected);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
