package payment.service;

import payment.dto.CreatePaymentForm;
import payment.dto.PayPaymentForm;
import payment.dto.TransferForm;
import payment.entity.PaymentEntity;
import payment.model.Message;
import payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        PaymentEntity paymentEntity=paymentRepository.getFirstByOrderid(form.getOrderid());
        if(paymentEntity==null) return Message.builder().code(400).content("Khong tim thay hoa don").build();
        TransferForm transferForm=TransferForm.builder()
                .sendernumber(form.getCreditcard())
                .cvc(form.getCvc())
                .receivernumber("6587-2880-1111-1111")
                .money(paymentEntity.getAmount().toString()).build();
        Message message=bankService.transfer(transferForm);
        if (message.getCode()==200){
            paymentEntity.setCreditcard(form.getCreditcard());
            paymentEntity.setPaid(true);
            paymentRepository.saveAndFlush(paymentEntity);
            return Message.builder().code(200).content("Thanh toan thanh cong").build();
        }
        return message;
    }
    public Message checkPaidOrder(Integer orderid){
        PaymentEntity paymentEntity=paymentRepository.getFirstByOrderid(orderid);
        if(paymentEntity.getPaid()==true) return Message.builder().code(200).build();
        else return Message.builder().code(400).content("Hoa don chua duoc thanh toan").build();
    }
}
