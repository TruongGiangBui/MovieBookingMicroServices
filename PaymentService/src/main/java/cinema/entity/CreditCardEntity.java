package cinema.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name="credit_cards")
@Builder
public class CreditCardEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String number;
    private String cvc;
    @Column(name="holder_name")
    private String holdername;
    private String currency;
    private BigDecimal balance;
    @Column(name = "expired_date")
    private Timestamp expireddate;
}
