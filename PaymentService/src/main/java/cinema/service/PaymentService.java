package cinema.service;

import cinema.dto.CreatePaymentForm;
import cinema.dto.PayPaymentForm;
import cinema.dto.TransferForm;
import cinema.entity.PaymentEntity;
import cinema.model.Message;
import cinema.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BankService bankService;
    public Message createPayment(CreatePaymentForm form){
        try{
            PaymentEntity paymentEntity=PaymentEntity.builder()
                    .paid(false)
                    .currency(form.getCcy())
                    .creditcard("")
                    .amount(form.getAmount())
                    .orderid(form.getOrderid())
                    .description(form.getDescription())
                    .build();
            paymentRepository.saveAndFlush(paymentEntity);
            return Message.builder().code(200).createdID(paymentEntity.getId()).build();
        }catch (Exception e){
            return Message.builder().code(500).build();
        }
    }
    public Message payPayment(PayPaymentForm form){
        PaymentEntity paymentEntity=paymentRepository.getOne(form.getPaymentid());
        paymentEntity.setCreditcard(form.getCreditcard());
        paymentEntity.setPaid(true);
        paymentRepository.saveAndFlush(paymentEntity);
        TransferForm transferForm=TransferForm.builder()
                .sendernumber(form.getCreditcard())
                .cvc(form.getCvc())
                .receivernumber("6587-2880-1111-1111")
                .money(paymentEntity.getAmount().toString()).build();
        return bankService.transfer(transferForm);
    }
}
