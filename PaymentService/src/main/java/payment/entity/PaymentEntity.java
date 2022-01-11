package payment.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="payments")
@Builder
public class PaymentEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String currency;
    @Column(name = "credit_card")
    private String creditcard;
    private BigDecimal amount;
    private Boolean paid;
    @Column(name = "order_id")
    private Integer orderid;
    private String description;
}
