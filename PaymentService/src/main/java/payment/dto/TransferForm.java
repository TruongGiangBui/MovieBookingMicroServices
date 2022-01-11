package payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransferForm {
    private String sendernumber;
    private String cvc;
    private String receivernumber;
    private String money;
}
