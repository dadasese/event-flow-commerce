package com.event.orderservice.application;

import com.event.orderservice.domain.InventoryClientPort;
import com.event.orderservice.domain.Order;
import com.event.orderservice.domain.OrderRepositoryPort;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class CreateOrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final InventoryClientPort inventoryClient;

    public CreateOrderUseCase(OrderRepositoryPort orderRepository, InventoryClientPort inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public Mono<Order> execute(String customerId, String productId, int quantity, BigDecimal amount) {
        return inventoryClient.checkAndReserveStock(productId, quantity)
                .flatMap(reserved -> {
                    if (!reserved) {
                        return Mono.error(new IllegalStateException(
                                "Insufficient stock for product " + productId));
                    }
                    Order confirmedOrder = Order.createNew(customerId, amount).markConfirmed();
                    return orderRepository.save(confirmedOrder);
                });
    }
}
