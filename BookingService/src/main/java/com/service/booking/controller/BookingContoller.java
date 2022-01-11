package com.service.booking.controller;

import com.service.booking.dto.CreateOrderForm;
import com.service.booking.dto.CreateOrderResponse;
import com.service.booking.model.Order;
import com.service.booking.model.Ticket;
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
    @GetMapping("/order/getTickets")
    @ResponseBody
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam("orderid") Integer orderid){
        List<Ticket> tickets=bookingService.getTickets(orderid);
        if(tickets==null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        if(tickets.size()>0){
            return new ResponseEntity<>(tickets,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/order/callafterPayorder")
    @ResponseBody
    public ResponseEntity<String> callafterPayorder(@RequestBody Integer orderid){
        bookingService.callafterPayorder(orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/order/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrderbyId(@PathVariable("id") Integer id){
        return new ResponseEntity<>(bookingService.getOrderbyId(id),HttpStatus.OK);
    }
}
