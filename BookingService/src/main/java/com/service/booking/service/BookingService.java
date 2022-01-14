package com.service.booking.service;

import com.google.gson.Gson;
import com.service.booking.dto.CreateOrderForm;
import com.service.booking.dto.CreateOrderResponse;
import com.service.booking.dto.CreatePaymentForm;
import com.service.booking.dto.SelectSeatForm;
import com.service.booking.entity.OrderEntity;
import com.service.booking.entity.TicketEntity;
import com.service.booking.model.*;
import com.service.booking.repository.OrderRepository;
import com.service.booking.repository.TicketRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TicketRepository ticketRepository;
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
    public Message cancelOrder(Integer orderid){
        try{
            Gson gson=new Gson();
            Order order=Order.convertEntity(orderRepository.getOne(orderid));

            SelectSeatForm selectSeatForm=new SelectSeatForm(order.getSchedule(),order.getSeatslist());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(gson.toJson(selectSeatForm), headers);
            restTemplate.postForEntity("http://cinema-service/cinemas/schedules/unselect", request , String.class );

            return Message.builder().code(200).build();
        }catch (Exception e){
            return Message.builder().code(404).build();
        }
    }
    public void callafterPayorder(Integer orderid){
        OrderEntity orderEntity=orderRepository.getOne(orderid);
        orderEntity.setCompletlypayment(true);
        orderRepository.saveAndFlush(orderEntity);
        Gson gson=new Gson();
        SelectSeatForm selectSeatForm=new SelectSeatForm(orderEntity.getSchedule(),Order.convertEntity(orderEntity).getSeatslist());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(gson.toJson(selectSeatForm), headers);
        restTemplate.postForEntity("http://cinema-service/cinemas/schedules/order", request , String.class );
    }
    public Order getOrderbyId(Integer orderid){
        return Order.convertEntity(orderRepository.getOne(orderid));
    }
    public List<Ticket> getTickets(Integer orderid){
        List<TicketEntity> ticketEntities=ticketRepository.findTicketEntitiesByOrderid(orderid);
        if(ticketEntities.size()>0){
            List<Ticket> tickets=new ArrayList<>();
            for(TicketEntity ticketEntity:ticketEntities) tickets.add(Ticket.convertEntity(ticketEntity));
            return tickets;
        }else {
            Message message=null;
            try {
                message=restTemplate.getForObject("http://payment-service/payment/checkPaidOrder?orderid="+orderid, Message.class );
            }catch (Exception e){
                return null;
            }
            if(message.getCode()==200){
                OrderEntity orderEntity=orderRepository.getOne(orderid);
                Order order=Order.convertEntity(orderEntity);
                return generateTicket(order);
            }
        }
        return null;
    }
    public List<Ticket> generateTicket(Order order){
        List<Ticket> tickets=new ArrayList<>();
        Schedule schedule=restTemplate.getForObject("http://cinema-service/cinemas/schedules/{id}",Schedule.class,order.getSchedule());
        Movie movie=restTemplate.getForObject("http://movies-service/api/movies/{id}",Movie.class,schedule.getMovieid());
        for (Integer seat:order.getSeatslist()){
            Ticket ticket=Ticket.builder()
                    .cinema(schedule.getCinemaid().toString())
                    .cinemaroom(schedule.getCinemaroom())
                    .seat(seat.toString())
                    .orderid(order.getId())
                    .movie(movie.getTitle())
                    .schedule(schedule.getStarttime().toString())
                    .build();
            TicketEntity ticketEntity= ticket.toEntity();
            ticketRepository.saveAndFlush(ticketEntity);
            ticket.setId(ticketEntity.getId());
            tickets.add(ticket);
        }
        return tickets;
    }
}
