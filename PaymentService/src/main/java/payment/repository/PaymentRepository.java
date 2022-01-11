package payment.repository;

import payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Integer> {
    PaymentEntity getFirstByOrderid(Integer orderid);
}
