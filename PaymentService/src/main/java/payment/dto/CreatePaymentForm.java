package payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentForm {
    Integer orderid;
    BigDecimal amount;
    String ccy;
    String description;
}
