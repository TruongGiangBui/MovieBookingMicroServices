package com.service.cinema.controller;

import com.service.cinema.model.AddScheduleForm;
import com.service.cinema.model.Schedule;
import com.service.cinema.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaAdminController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("cinemas/{cinemaid}/schedules")
    @ResponseBody
    public ResponseEntity<String> addSchedule(@RequestBody AddScheduleForm addScheduleForm){
        try{
            scheduleService.addSchedule(addScheduleForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
