package com.event.orderservice.domain;

import reactor.core.publisher.Mono;

public interface InventoryClientPort {
    Mono<Boolean> checkAndReserveStock(String productId, int quantity);
}
