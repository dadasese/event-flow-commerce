package com.event.orderservice.domain;

import reactor.core.publisher.Mono;

/**
 * Outbound port. The application layer depends on this interface only —
 * it has no idea whether the implementation is Postgres, an in-memory map, or anything else.
 */
public interface OrderRepositoryPort {
    Mono<Order> save(Order order);
    Mono<Order> findById(String id);
}
