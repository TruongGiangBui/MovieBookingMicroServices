package payment.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import payment.dto.CreatePaymentForm;
import payment.dto.PayPaymentForm;
import payment.model.Message;
import payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class PaymentController {
    @Autowired
    private RestTemplate restTemplate;
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
        if(message.getCode()==200) {
//            /order/callafterPayorder
            restTemplate.postForEntity("http://booking-service/order/callafterPayorder", form.getOrderid(), String.class);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/payment/checkPaidOrder")
    @ResponseBody
    public ResponseEntity<Message> checkPaidOrder(@RequestParam("orderid") Integer orderid){
        Message message=paymentService.checkPaidOrder(orderid);
        if(message.getCode()==200)
            return new ResponseEntity<>(message,HttpStatus.OK);
        else
            return new ResponseEntity<>(message,HttpStatus.NOT_ACCEPTABLE);
    }
}
