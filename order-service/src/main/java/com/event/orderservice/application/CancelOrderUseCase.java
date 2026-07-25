package com.event.orderservice.application;

import com.event.orderservice.domain.Order;
import com.event.orderservice.domain.OrderRepositoryPort;
import reactor.core.publisher.Mono;

public class CancelOrderUseCase {

    private final OrderRepositoryPort orderRepository;

    public CancelOrderUseCase(OrderRepositoryPort orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Mono<Order> execute(String orderId) {
        return orderRepository.findById(orderId)
                .flatMap(order -> orderRepository.save(order.markCancelled()));
    }
}