package com.service.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayPaymentForm {
    Integer paymentid;
    String creditcard;
    String cvc;
}
