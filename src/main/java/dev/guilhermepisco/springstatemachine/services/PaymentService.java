package dev.guilhermepisco.springstatemachine.services;


import dev.guilhermepisco.springstatemachine.domain.Payment;
import dev.guilhermepisco.springstatemachine.domain.PaymentEvent;
import dev.guilhermepisco.springstatemachine.domain.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface PaymentService {

    Payment newPayment(Payment payment);

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId);
}
