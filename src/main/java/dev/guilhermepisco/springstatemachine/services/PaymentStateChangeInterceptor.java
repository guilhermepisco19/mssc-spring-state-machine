package dev.guilhermepisco.springstatemachine.services;

import dev.guilhermepisco.springstatemachine.domain.Payment;
import dev.guilhermepisco.springstatemachine.domain.PaymentEvent;
import dev.guilhermepisco.springstatemachine.domain.PaymentState;
import dev.guilhermepisco.springstatemachine.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message,
                               Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine, StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        Optional.ofNullable(message)
                .flatMap(
                msg -> Optional.ofNullable((Long) msg.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1L)))
                            .ifPresent(paymentId -> {
                                Payment payment = paymentRepository.getReferenceById(paymentId);
                                payment.setState(state.getId());
                                paymentRepository.save(payment);
                            });
    }
}
