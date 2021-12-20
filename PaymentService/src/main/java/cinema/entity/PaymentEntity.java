package cinema.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

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
