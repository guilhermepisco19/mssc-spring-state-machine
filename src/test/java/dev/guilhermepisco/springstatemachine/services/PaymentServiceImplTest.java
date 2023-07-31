package dev.guilhermepisco.springstatemachine.services;

import dev.guilhermepisco.springstatemachine.domain.Payment;
import dev.guilhermepisco.springstatemachine.domain.PaymentEvent;
import dev.guilhermepisco.springstatemachine.domain.PaymentState;
import dev.guilhermepisco.springstatemachine.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder()
                .amount(BigDecimal.valueOf(12.99))
                .build();

    }

    @Transactional
    @Test
    void preAuth() {

        Payment savedPayment = paymentService.newPayment(payment);

        Assertions.assertEquals(PaymentState.NEW, savedPayment.getState());

        StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = paymentRepository.getReferenceById(savedPayment.getId());

        Assertions.assertEquals(PaymentState.PRE_AUTH, preAuthedPayment.getState());

        System.out.println(sm.getState().getId());

        System.out.println(preAuthedPayment);
    }
}