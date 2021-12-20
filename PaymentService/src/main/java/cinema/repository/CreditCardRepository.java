package cinema.repository;

import cinema.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCardEntity,Integer> {
    CreditCardEntity findCreditCardEntitiesByNumberAndCvc(String number,String cvc);
    CreditCardEntity findCreditCardEntitiesByNumber(String number);
}
