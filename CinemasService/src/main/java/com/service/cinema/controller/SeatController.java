package com.service.cinema.controller;

import com.google.gson.Gson;
import com.service.cinema.dto.SelectSeatForm;
import com.service.cinema.model.Schedule;
import com.service.cinema.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SeatController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @PostMapping("/cinemas/schedules/select")
    @ResponseBody
    public ResponseEntity<String> selectSeats(@RequestBody SelectSeatForm form){
        try{
            scheduleService.selectSeats(form.getScheduleid(),form.getSelected());
            updateStatus(form.getScheduleid());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/cinemas/schedules/order")
    @ResponseBody
    public ResponseEntity<String> orderSeats(@RequestBody SelectSeatForm form){
        try{
            scheduleService.orderSeats(form.getScheduleid(),form.getSelected());
            updateStatus(form.getScheduleid());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/cinemas/schedules/unselect")
    @ResponseBody
    public ResponseEntity<String> unselectSeats(@RequestBody SelectSeatForm form){
        try{
            scheduleService.unselectSeats(form.getScheduleid(),form.getSelected());
            updateStatus(form.getScheduleid());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    public void updateStatus(Integer id) {
        Gson gson=new Gson();
        simpMessagingTemplate.convertAndSend("/topic/reply/"+id, gson.toJson(scheduleService.getScheduleById(id)));
    }
}
