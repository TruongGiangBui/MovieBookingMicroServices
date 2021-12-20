package com.service.booking.service;

import com.google.gson.Gson;
import com.service.booking.dto.CreateOrderForm;
import com.service.booking.dto.CreateOrderResponse;
import com.service.booking.dto.CreatePaymentForm;
import com.service.booking.dto.SelectSeatForm;
import com.service.booking.entity.OrderEntity;
import com.service.booking.model.Message;
import com.service.booking.model.Order;
import com.service.booking.model.Schedule;
import com.service.booking.repository.OrderRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class BookingService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderForm createOrderForm){
        Gson gson=new Gson();
        Schedule schedule=restTemplate.getForObject("http://cinema-service/cinemas/schedules/{id}",Schedule.class,createOrderForm.getSchedule_id());
        Order order= Order.builder()
                .schedule(schedule.getId())
                .seatslist(createOrderForm.getSeats())
                .totalamount(schedule.getPrice().multiply(new BigDecimal(createOrderForm.getSeats().size())))
                .completlypayment(false)
                .build();

        OrderEntity orderEntity=order.toEntity();
        orderRepository.saveAndFlush(orderEntity);

        SelectSeatForm selectSeatForm=new SelectSeatForm(schedule.getId(),createOrderForm.getSeats());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(gson.toJson(selectSeatForm), headers);
        restTemplate.postForEntity("http://cinema-service/cinemas/schedules/select", request , String.class );

        CreatePaymentForm createPaymentForm=CreatePaymentForm.builder()
                .orderid(orderEntity.getId())
                .amount(order.getTotalamount())
                .ccy("VND")
                .description(createOrderForm.getSeats().toString()).build();
        HttpHeaders paymentheaders = new HttpHeaders();
        paymentheaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> paymentrequest = new HttpEntity<String>(gson.toJson(createPaymentForm), paymentheaders);

        Message message=restTemplate.postForEntity("http://payment-service/payment/create", paymentrequest , Message.class ).getBody();

        return CreateOrderResponse.builder()
                .id(orderEntity.getId())
                .paymentid(message.getCreatedID())
                .schedule(orderEntity.getSchedule())
                .totalamount(orderEntity.getTotalamount())
                .seatslist(StringUtils.join(order.getSeatslist(), ",")).build();
    }
}
