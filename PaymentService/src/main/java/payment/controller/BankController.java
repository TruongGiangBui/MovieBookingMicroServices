package payment.controller;

import payment.dto.AddCreditCardForm;
import payment.dto.DepositForm;
import payment.dto.TransferForm;
import payment.model.CreditCard;
import payment.model.Message;
import payment.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class BankController {
    @Autowired
    private BankService bankService;
    @GetMapping("/balance/{number}")
    @ResponseBody
    public ResponseEntity<Message> getBalance(@PathVariable("number") String number){
        Message message=bankService.getBalance(number);
        if(message.getCode()==200){
            return new ResponseEntity<>(message,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/creditcard/info/{number}/{cvc}")
    @ResponseBody
    public ResponseEntity<CreditCard> getCardInfo(@PathVariable("number") String number,
                                              @PathVariable("cvc") String cvc){
        CreditCard creditCard=bankService.getCardInfo(number,cvc);
        if(creditCard!=null){
            return new ResponseEntity<>(creditCard, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/creditcard/create")
    @ResponseBody
    public ResponseEntity<CreditCard> createCard(@RequestBody AddCreditCardForm addCreditCardForm){
        return new ResponseEntity<>(bankService.createCard(addCreditCardForm),HttpStatus.OK);
    }
    @PostMapping("/deposit")
    @ResponseBody
    public ResponseEntity<Message> deposit(@RequestBody DepositForm depositForm){
        Message message=bankService.deposit(depositForm);
        switch (message.getCode()){
            case 200:
                return new ResponseEntity<>(message,HttpStatus.OK);
            case 404:
                return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<Message> transfer(@RequestBody TransferForm transferForm){
        Message message=bankService.transfer(transferForm);
        switch (message.getCode()){
            case 200:
                return new ResponseEntity<>(message,HttpStatus.OK);
            case 404:
                return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
            default:
                return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
