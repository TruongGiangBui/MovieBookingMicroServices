package com.service.booking.controller;

import com.service.booking.dto.CreateOrderForm;
import com.service.booking.dto.CreateOrderResponse;
import com.service.booking.model.Order;
import com.service.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookingContoller {
    @Autowired
    private BookingService bookingService;
    @PostMapping("/order/create")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderForm form){
        CreateOrderResponse createOrderResponse=  bookingService.createOrder(form);
        return new ResponseEntity<>(createOrderResponse,HttpStatus.OK);
    }
}
