package com.service.admin.controller;

import com.service.admin.dto.GenScheduleForm;
import com.service.admin.model.Message;
import com.service.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    @PostMapping("/admin/generateSchedule")
    public ResponseEntity<Message> generateSchedule(@RequestBody GenScheduleForm form){
        Message message=adminService.generateSchedule(form);
        if(message.getCode()==200){
            return new ResponseEntity<>(message,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
