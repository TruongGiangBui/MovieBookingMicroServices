package com.service.booking.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name="orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer schedule;
    private String seats;
    @Column(name = "total_amount")
    private BigDecimal totalamount;
    @Column(name = "completly_payment")
    private Boolean completlypayment;
    @OneToMany(mappedBy = "orderEntity",fetch = FetchType.LAZY)
    private Set<TicketEntity> ticketEntities;
}
