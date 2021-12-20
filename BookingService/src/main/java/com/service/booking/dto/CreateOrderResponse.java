package com.service.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateOrderResponse {
    private Integer id;
    private Integer schedule;
    private String seatslist;
    private BigDecimal totalamount;
    private Integer paymentid;
}
