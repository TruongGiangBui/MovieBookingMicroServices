package payment.service;

import payment.dto.AddCreditCardForm;
import payment.dto.DepositForm;
import payment.dto.TransferForm;
import payment.entity.CreditCardEntity;
import payment.model.CreditCard;
import payment.model.Message;
import payment.repository.CreditCardRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
@Service
public class BankService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    public CreditCard createCard(AddCreditCardForm addCreditCardForm){
        Faker faker=new Faker();
        CreditCardEntity creditCardEntity=CreditCardEntity.builder()
                .number(faker.finance().creditCard())
                .cvc(String.valueOf(faker.number().numberBetween(100,999)))
                .holdername(addCreditCardForm.getHoldername())
                .currency(addCreditCardForm.getCurrency())
                .balance(new BigDecimal(0))
                .expireddate(new Timestamp(System.currentTimeMillis()+157784760000L))
                .build();
        creditCardRepository.saveAndFlush(creditCardEntity);
        return CreditCard.convertEntity(creditCardEntity);
    }
    public Message getBalance(String creditcard){
        CreditCardEntity creditCardEntity=creditCardRepository.findCreditCardEntitiesByNumber(creditcard);
        if(creditcard!=null){
            return Message.builder().code(200).content(creditCardEntity.getBalance().toString()).build();
        }else {
            return Message.builder().code(404).content("Card isn't found").build();
        }
    }
    public Message deposit(DepositForm depositForm){
        CreditCardEntity creditCardEntity=creditCardRepository.findCreditCardEntitiesByNumberAndCvc(depositForm.getNumber(),depositForm.getCvc());
        try {
            if(creditCardEntity!=null){
                creditCardEntity.setBalance(creditCardEntity.getBalance().add(new BigDecimal(depositForm.getMoney())));
                creditCardRepository.saveAndFlush(creditCardEntity);
                return Message.builder().code(200).content(creditCardEntity.getBalance().toString()).build();
            }else {
                return Message.builder().code(404).content("Card isn't accepted").build();
            }
        }catch (Exception e){
            return Message.builder().code(500).content(e.getMessage()).build();
        }
    }
    public CreditCard getCardInfo(String number,String cvc){
        CreditCardEntity creditCardEntity=creditCardRepository.findCreditCardEntitiesByNumberAndCvc(number,cvc);
        if(creditCardEntity!=null) return CreditCard.convertEntity(creditCardEntity);
        else return null;
    }
    @Transactional
    @Modifying
    public Message transfer(TransferForm transferForm){
        try{
            CreditCardEntity sender=creditCardRepository.findCreditCardEntitiesByNumberAndCvc(transferForm.getSendernumber(),transferForm.getCvc());
            CreditCardEntity receiver=creditCardRepository.findCreditCardEntitiesByNumber(transferForm.getReceivernumber());
            if(sender==null||receiver==null){
                return Message.builder().code(404).content("Card isn't accepted").build();
            }else {
                BigDecimal transfermoney = new BigDecimal(transferForm.getMoney());
                if (sender.getBalance().compareTo(transfermoney) > 0) {
                    sender.setBalance(sender.getBalance().subtract(transfermoney));
                    receiver.setBalance(receiver.getBalance().add(transfermoney));
                } else {
                    return Message.builder().code(404).content("Not enough balance").build();
                }
                creditCardRepository.saveAndFlush(sender);
                creditCardRepository.saveAndFlush(receiver);
                return Message.builder().code(200).content(sender.getBalance().toString()).build();
            }
        }catch (Exception e){
            return Message.builder().code(500).content(e.getMessage()).build();
        }
    }
}
