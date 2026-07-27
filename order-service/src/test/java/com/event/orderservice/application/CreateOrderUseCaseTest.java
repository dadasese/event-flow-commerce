package com.event.orderservice.application;

import com.event.orderservice.domain.InventoryClientPort;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepository;

    @Mock
    private InventoryClientPort inventoryClient;

    @Test
    void createsConfirmedOrder_whenStockIsAvailable() {
        CreateOrderUseCase useCase = new CreateOrderUseCase(orderRepository, inventoryClient);

        when(inventoryClient.checkAndReserveStock("PROD-1", 2)).thenReturn(Mono.just(true));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<Order> result = useCase.execute("CUST-1", "PROD-1", 2, BigDecimal.valueOf(99.90));

        StepVerifier.create(result)
                .assertNext(order -> Assertions.assertEquals(OrderStatus.CONFIRMED, order.getStatus()))
                .verifyComplete();

        verify(inventoryClient).checkAndReserveStock("PROD-1", 2);
    }

    @Test
    void failsFast_whenStockIsUnavailable() {
        CreateOrderUseCase useCase = new CreateOrderUseCase(orderRepository, inventoryClient);
        when(inventoryClient.checkAndReserveStock("PROD-2", 5)).thenReturn(Mono.just(false));

        Mono<Order> result = useCase.execute("CUST-2", "PROD-2", 5, BigDecimal.TEN);

        StepVerifier.create(result)
                .expectErrorMatches(ex -> ex instanceof IllegalStateException)
                .verify();

        verify(orderRepository, never()).save(any());
    }
}
