package com.event.orderservice.application;

import com.event.orderservice.domain.Order;
import com.event.orderservice.domain.OrderRepositoryPort;
import com.event.orderservice.domain.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepository;

    @Test
    void cancelsConfirmedOrder_andPersistsIt() {
        CancelOrderUseCase useCase = new CancelOrderUseCase(orderRepository);

        Order confirmedOrder = new Order("ORD-1", "CUST-1", BigDecimal.TEN,
                OrderStatus.CONFIRMED, Instant.now(), 0);
        when(orderRepository.findById("ORD-1")).thenReturn(Mono.just(confirmedOrder));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(useCase.execute("ORD-1"))
                .assertNext(order -> Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus()))
                .verifyComplete();

        verify(orderRepository).save(argThat(o -> o.getStatus() == OrderStatus.CANCELLED));
    }
}