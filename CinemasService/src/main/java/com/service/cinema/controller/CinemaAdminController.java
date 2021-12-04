package com.service.cinema.controller;

import com.service.cinema.dto.AddScheduleForm;
import com.service.cinema.service.CinemaService;
import com.service.cinema.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class CinemaAdminController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CinemaService cinemaService;
    @PostMapping("cinemas/schedules")
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
    @GetMapping("/fake")
    @ResponseBody
    public String test(){
        cinemaService.fakeCountry();
        return "";
    }
}
