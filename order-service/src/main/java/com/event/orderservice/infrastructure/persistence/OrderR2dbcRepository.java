package com.event.orderservice.infrastructure.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderR2dbcRepository extends ReactiveCrudRepository<OrderEntity, String> {
}
