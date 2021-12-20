package cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositForm {
    private String number;
    private String cvc;
    private String money;
}
