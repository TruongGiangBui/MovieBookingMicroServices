package com.service.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreatePaymentForm {
    Integer orderid;
    BigDecimal amount;
    String ccy;
    String description;
}
