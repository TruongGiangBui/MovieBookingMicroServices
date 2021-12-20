package com.service.booking.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="tickets")
public class TicketEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String cinema;
    private String movie;
    private String schedule;
    @Column(name = "cinema_room")
    private Integer cinemaroom;
    private String seat;
    @Column(name = "order_id")
    private Integer orderid;
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id",referencedColumnName = "id",insertable=false, updatable=false)
    private OrderEntity orderEntity;
}
