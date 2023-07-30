package dev.guilhermepisco.springstatemachine.config;

import dev.guilhermepisco.springstatemachine.domain.PaymentEvent;
import dev.guilhermepisco.springstatemachine.domain.PaymentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootTest
class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<PaymentState, PaymentEvent> factory;

    @Test
    void testNewStateMachine() {
        StateMachine<PaymentState, PaymentEvent> sm = factory.getStateMachine(UUID.randomUUID());

        sm.startReactively().subscribe();

        System.out.println(sm.getState());

        Message<PaymentEvent> preAuthorizeMsg = MessageBuilder.withPayload(PaymentEvent.PRE_AUTHORIZE).build();
        sm.sendEvent(Mono.just(preAuthorizeMsg)).subscribe();

        System.out.println(sm.getState());

        Message<PaymentEvent> preAuthApprovedMsg = MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_APPROVED).build();
        sm.sendEvent(Mono.just(preAuthApprovedMsg)).subscribe();

        System.out.println(sm.getState());

    }

}