package com.event.inventoryservice.domain;

import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {
    Mono<Product> findById(String id);

    Mono<Product> save(Product product);

    Mono<Void> deleteById(String id);

    Mono<Boolean> reserveStock(String id, int quantity);
}
