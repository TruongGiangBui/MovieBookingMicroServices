package payment.model;

import payment.entity.CreditCardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCard {
    private String number;
    private String cvc;
    @Column(name="holder_name")
    private String holdername;
    private String currency;
    private BigDecimal balance;
    @Column(name = "expired_date")
    private Timestamp expireddate;
    public static CreditCard convertEntity(CreditCardEntity creditCardEntity){
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(creditCardEntity,CreditCard.class);
    }
    public CreditCardEntity toEntity(){
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(this,CreditCardEntity.class);
    }
}
