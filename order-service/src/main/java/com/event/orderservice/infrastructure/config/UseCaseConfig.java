package com.event.orderservice.infrastructure.config;

import com.event.orderservice.application.CreateOrderUseCase;
import com.event.orderservice.domain.InventoryClientPort;
import com.event.orderservice.domain.OrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is where ports get wired to their adapters and use cases get built.
 * Keeping this wiring in one place (rather than annotating the use case itself)
 * is what makes the application/domain layers portable and framework-free.
 */
@Configuration
public class UseCaseConfig {

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort orderRepositoryPort,
                                                 InventoryClientPort inventoryClientPort) {
        return new CreateOrderUseCase(orderRepositoryPort, inventoryClientPort);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new CancelOrderUseCase(orderRepositoryPort);
    }
}
