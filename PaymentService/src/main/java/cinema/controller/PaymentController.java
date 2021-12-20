package cinema.controller;

import cinema.dto.AddCreditCardForm;
import cinema.dto.CreatePaymentForm;
import cinema.dto.PayPaymentForm;
import cinema.model.CreditCard;
import cinema.model.Message;
import cinema.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/payment/create")
    @ResponseBody
    public ResponseEntity<Message> createCard(@RequestBody CreatePaymentForm form){
        Message message=paymentService.createPayment(form);
        if(message.getCode()==200)
            return new ResponseEntity<>(message,HttpStatus.OK);
        else
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/payment/pay")
    @ResponseBody
    public ResponseEntity<Message> payPayment(@RequestBody PayPaymentForm form){
        Message message=paymentService.payPayment(form);
        if(message.getCode()==200)
            return new ResponseEntity<>(message,HttpStatus.OK);
        else
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
