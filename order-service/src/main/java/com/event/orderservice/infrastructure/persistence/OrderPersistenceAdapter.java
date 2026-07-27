package com.event.orderservice.infrastructure.persistence;

import com.event.orderservice.domain.Order;
import com.event.orderservice.domain.OrderRepositoryPort;
import com.event.orderservice.domain.OrderStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Outbound adapter — implements the domain's port using R2DBC.
 * Translates between the domain Order and the persistence OrderEntity.
 */
@Component
public class OrderPersistenceAdapter implements OrderRepositoryPort {

    private final OrderR2dbcRepository repository;

    public OrderPersistenceAdapter(OrderR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Order> save(Order order) {
        return repository.save(toEntity(order)).map(this::toDomain);
    }

    @Override
    public Mono<Order> findById(String id) {
        return repository.findById(id).map(this::toDomain);
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setAmount(order.getAmount());
        entity.setStatus(order.getStatus().name());
        entity.setCreatedAt(order.getCreatedAt());
        return entity;
    }

    private Order toDomain(OrderEntity entity) {
        return new Order(entity.getId(), entity.getCustomerId(), entity.getAmount(),
                OrderStatus.valueOf(entity.getStatus()), entity.getCreatedAt(),
                entity.getVersion() == null ? 0 : entity.getVersion());
    }
}
