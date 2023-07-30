package dev.guilhermepisco.springstatemachine.repository;

import dev.guilhermepisco.springstatemachine.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
